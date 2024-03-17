package esperer.concurrency.selfstudy.service

import esperer.concurrency.selfstudy.domain.SelfStudyRepository
import esperer.concurrency.selfstudy.dto.CreateSelfStudyRequest
import esperer.concurrency.selfstudy.dto.SelfStudyResponse
import esperer.concurrency.user.UserRepository
import org.redisson.client.RedisClient
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class SelfStudyService(
    private val selfStudyRepository: SelfStudyRepository,
    private val userRepository: UserRepository
) {

    fun reserve(id: Long, request: CreateSelfStudyRequest): Mono<SelfStudyResponse> =
        checkLimit(id)
            .flatMap { checkUser(request.userId) }
            .then(selfStudyRepository.findById(id))
            .flatMap { selfStudy ->
                selfStudy.plusRoomCount()
                userRepository.findById(request.userId)
                    .flatMap { user ->
                        user.reserveSelfStudy(id)
                        userRepository.save(user)
                    }
                    .then(selfStudyRepository.save(selfStudy))
            }.map {
                SelfStudyResponse(it.id, request.userId, it.roomCount, it.limit)
            }


    private fun checkLimit(id: Long) =
        selfStudyRepository.findById(id)
            .flatMap {
                if(it.roomCount >= it.limit) Mono.just(false)
                else Mono.just(true)
            }.checkTemplate(RuntimeException("자리가 남아있지 않아요"), true)

    private fun checkUser(userId: Long) =
        userRepository.findById(userId)
            .flatMap {
                if(it.selfStudyId == null) Mono.just(true) else Mono.just(false)
            }.checkTemplate(RuntimeException("유저가 이미 신청한 상태에요"), true)

    private fun <T: RuntimeException> Mono<Boolean>.checkTemplate(onFailed: T, failedCondition: Boolean = false): Mono<Any> =
        flatMap {
            if(it == failedCondition) Mono.error(onFailed)
            else Mono.just(Any())
        }
}