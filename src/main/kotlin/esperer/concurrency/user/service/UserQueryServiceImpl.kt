package esperer.concurrency.user.service

import esperer.concurrency.user.domain.UserRepository
import esperer.concurrency.user.dto.UserResponse
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UserQueryServiceImpl(
    private val userRepository: UserRepository
) : UserQueryService {

    override fun getUserInfo(request: Mono<Long>): Mono<UserResponse> =
        userRepository.findById(request)
            .map { UserResponse(it.id, it.name) }

}