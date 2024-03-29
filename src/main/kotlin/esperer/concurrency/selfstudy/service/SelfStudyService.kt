package esperer.concurrency.selfstudy.service

import esperer.concurrency.lock.DistributedLock
import esperer.concurrency.selfstudy.domain.SelfStudyRepository
import esperer.concurrency.selfstudy.domain.SelfStudyUser
import esperer.concurrency.selfstudy.domain.SelfStudyUserRepository
import esperer.concurrency.selfstudy.dto.CreateSelfStudyRequest
import esperer.concurrency.selfstudy.dto.SelfStudyResponse
import esperer.concurrency.user.domain.UserRepository
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class SelfStudyService(
    private val selfStudyRepository: SelfStudyRepository,
    private val userRepository: UserRepository,
    private val selfStudyUserRepository: SelfStudyUserRepository
) {

    @DistributedLock(key = "selfstudy")
    fun reserve(id: Long, request: CreateSelfStudyRequest): Mono<SelfStudyResponse> =
        checkLimit(id)
            .flatMap { checkUser(request.userId) }
            .then(selfStudyRepository.findById(id))
            .flatMap { selfStudy ->
                selfStudy.plusRoomCount()
                userRepository.findById(request.userId)
                    .flatMap { user ->
                        selfStudyUserRepository.save(
                            SelfStudyUser(
                                selfStudyId = id,
                                userId = user.id
                            )
                        )
                    }
                    .then(selfStudyRepository.save(selfStudy))
            }.map {
                SelfStudyResponse(it.id, request.userId, it.roomCount, it.limitCount)
            }


    private fun checkLimit(id: Long) =
        selfStudyRepository.findById(id)
            .flatMap {
                if(it.roomCount >= it.limitCount) Mono.just(false)
                else Mono.just(true)
            }.checkTemplate(RuntimeException("자리가 남아있지 않아요"), true)

    private fun checkUser(userId: Long) =
        selfStudyUserRepository.existsByUserId(userId)
            .checkTemplate(RuntimeException("유저가 이미 신청한 상태에요"), true)

    private fun <T: RuntimeException> Mono<Boolean>.checkTemplate(onFailed: T, failedCondition: Boolean = false): Mono<Any> =
        flatMap {
            if(it == failedCondition) Mono.error(onFailed)
            else Mono.just(Any())
        }
}