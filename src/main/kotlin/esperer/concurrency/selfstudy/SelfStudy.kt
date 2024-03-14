package esperer.concurrency.selfstudy

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("tbl_selfstudy")
class SelfStudy(
    @field:Id
    val id: Long,

    val userId: Long,

    val roomNumber: Int
)