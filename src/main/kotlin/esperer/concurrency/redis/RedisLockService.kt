package esperer.concurrency.redis

import org.redisson.api.RLock
import org.redisson.api.RedissonClient
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class RedisLockService(
    private val redissonClient: RedissonClient
) {

    var waitTime: Long = 5L

    var leaseTime: Long = 3L

    fun <R> tryLockWith(
        lockName: String,
        task: () -> R
    ): R = tryLockWith(
        lockName = lockName,
        waitTime = waitTime,
        leaseTime = leaseTime,
        task = task
    )

    fun <R> tryLockWith(
        lockName: String,
        waitTime: Long,
        leaseTime: Long,
        task: () -> R,
    ): R {
        val rLock: RLock = redissonClient.getLock("redis_lock_$lockName") // Lock 호출
        val available: Boolean = rLock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS) // Lock 획득 시도
        if (!available) { // 획득 시도를 실패했을 경우 Exception 처리
            throw RuntimeException("락 획득 실패")
        }
        try {
            return task() // 전달 받은 람다 실행
        } finally {
            if (rLock.isHeldByCurrentThread) { // 해당 스레드가 Lock을 소유 중인지 확인
                rLock.unlock() // Lock 반환
            } else { // 스레드가 Lock을 소유 중이지 않을 경우, Exception (leaseTime을 넘은 경우)
                throw RuntimeException("스레드가 Lock을 소유하고있지 않습니다.")
            }
        }
    }

}