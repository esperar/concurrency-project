# Concurrency 프로젝트

1. Webflux - 비동기 API 만들기
2. 여러가지 방법들로 동시성 제어도 해보기

<br>

### Redis 분산락 방식 사용

![](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2Fb8XI8O%2FbtszM1YHVMa%2FMbKUTWapMhO8K7YkAg8cmK%2Fimg.png)

분산락은 **Race Condition이 발생할 때 하나의 공유 자원에 접근할 때 데이터에 결함이 발생하지 않도록 원자성을 보장하는 기법이다.**

대표적으로 사용해볼 저장소는 Redis로 싱글스레드 기반의 NoSQL이다.

레디스는 RedLock이라는 알고리즘을 제안하며 3가지 특성을 보장해야한다고 말한다.

1. 오직 한 순간에 하나의 작업자만이 락을 걸 수 있다.
2. 락 이후, 어떠한 문제로 인해 락을 못 풀고 종료된 경우라도 다른 작업자가 락을 획득할 수 있어야한다.
3. Redis 노드가 작동하는 한, 모든 작업자는 락을 걸고 해체가 가능해야한다.

즉 분산 락을 구현하기위해 락에 대한 정보를 Redis에 저장하고 있어야한다.

여러대의 서버들은 공통된 Redis를 바라보며 자신이 임계영역에 접근할 수 있는지 확인하고 이러한 부분에서 원자성을 확보한다.

> Redis도 물론 싱글스레드기반이기 때문에 단일 장애 지점이 될 수 있음을 고려하여 Failover용 추가 slave 자원을 구축해야한다.


<br>

## Trade-off

Redis Client를 활용해서 락을 구현해 볼 것이다. Redis Client인 Redission, Lettuce를 고려해 볼 수 있다.

둘은 공통적으로 스핀락을 사용해 락을 얻으려고 시도하며 스핀락에 단점인 과도한 부하를 최소화하기 위하여 적절한 주기로 적당량의 요청을 보낸다.

이는 서버 측에서 구독한 클라이언트에게 락을 사용해도 된다고 알림을 주어 락의 획득 여부를 클라이언트가 요청해서 확인하지 않아도 되게 하는 기법이다.

> 스핀락? 락의 획득 여부를 계속해서 무한루프를 돌면서 시도하는 방법

자 그러면 Lettuce vs Redission 어떤 기능을 사용해야할까?

### Lettuce

Lettuce는 Nettyr기반의 레디스 클라이언트며 요청을 넌블러킹으로 처리해 높은 성능을 자랑한다.

spring-data-redis를 추가했다면 기본적으로 redis client가 제공되는데 이것이 Lettuce기반이다.

- 장점으로는 redis 의존성을 추가하면 기본적으로 제공되므로 별도의 설정 없이 간단 구현 가능하다.
- 단점으로는 구현 방식에서 스핀락을 사용하기 때문에 레디스에 부하를 줄 수 있다.


### Redisson

Redisson은 pub/sub 기능을 제공한다.

이를 사용하면 스핀락 방식을 사용하지 않고 분산락을 구현할 수 있다.

직접 구현할 수도 있지만 redisson에서 이미 구현하여 제공해주고 있다.

```gradle
implementation 'org.redisson:redisson-spring-boot-starter:3.24.3'
```

다음과 같은 의존성을 추가하여 사용할 수 있다.

그렇기에 레디스에 부하를 덜 줄 수 있는 Redisson을 사용하겠다.

<br>

> 나중에 고려해봐야할 점 -> 서비스 내에서 클라이언트 클래스를 사용하지만 AOP를 통해 어노테이션 방식으로 락을 적용하는 라이브러리를 만들어봐도 괜찮을 것 같다. 