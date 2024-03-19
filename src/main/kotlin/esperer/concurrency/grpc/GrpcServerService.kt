package esperer.concurrency.grpc

import com.google.protobuf.Int64Value
import esperer.concurrency.user.service.UserQueryService
import esperer.lib.ReactorUserQueryServiceGrpc.UserQueryServiceImplBase
import esperer.lib.UserResponse
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono


@Service
class GrpcServerService(
    private val userQueryService: UserQueryService
) : UserQueryServiceImplBase() {

    override fun getUserInfo(request: Mono<Int64Value>): Mono<UserResponse> =
        userQueryService.getUserInfo(request.map { it.value })
            .map { it.toGrpc() }


    private fun esperer.concurrency.user.dto.UserResponse.toGrpc() =
        UserResponse.newBuilder()
            .setId(id)
            .setName(name)
            .build()
}