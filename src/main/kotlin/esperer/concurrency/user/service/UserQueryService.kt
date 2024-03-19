package esperer.concurrency.user.service

import esperer.concurrency.user.dto.UserResponse
import reactor.core.publisher.Mono

interface UserQueryService {
    fun getUserInfo(request: Mono<Long>): Mono<UserResponse>
}