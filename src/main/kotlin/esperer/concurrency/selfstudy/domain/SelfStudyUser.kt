package esperer.concurrency.selfstudy.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("tbl_selfstudy_user")
class SelfStudyUser(
    @field:Id
    val id: Long = 0,

    val selfStudyId: Long,

    val userId: Long,
) {
}