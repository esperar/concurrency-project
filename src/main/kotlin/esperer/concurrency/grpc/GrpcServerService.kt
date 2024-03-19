package esperer.concurrency.grpc

import esperer.concurrency.user.dto.UserRequest
import esperer.concurrency.user.dto.UserResponse
import esperer.concurrency.user.service.UserQueryService
import reactor.core.publisher.Mono


class GrpcServerService : UserQueryService {
    override fun getUserInfo(request: Mono<UserRequest>): Mono<UserResponse> {
        TODO("Not yet implemented")
    }
}