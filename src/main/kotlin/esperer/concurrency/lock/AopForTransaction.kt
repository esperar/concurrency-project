package esperer.concurrency.lock

import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Component
class AopForTransaction {

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun proceed(joinPoint: MethodInvocationProceedingJoinPoint) = joinPoint.proceed()
}