package esperer.concurrency.selfstudy.domain

import org.springframework.data.r2dbc.repository.R2dbcRepository
import reactor.core.publisher.Mono

interface SelfStudyUserRepository : R2dbcRepository<SelfStudyUser, Long> {
    fun existsByUserId(userId: Long): Mono<Boolean>
}