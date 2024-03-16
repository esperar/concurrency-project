package esperer.concurrency.selfstudy.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("tbl_selfstudy")
class SelfStudy(
    @field:Id
    val id: Long,

    val userId: Long,

    val roomCount: Int,

    val limit: Int,
)