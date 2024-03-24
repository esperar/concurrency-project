package esperer.concurrency.selfstudy.domain

import org.springframework.data.annotation.Id

class SelfStudyUser(
    @field:Id
    val id: Long = 0,

    val selfStudyId: Long,

    val userId: Long,
) {
}