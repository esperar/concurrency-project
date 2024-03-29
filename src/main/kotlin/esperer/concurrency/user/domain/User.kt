package esperer.concurrency.user.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("tbl_user")
class User(
    @field:Id
    val id: Long,

    val name: String,

    val password: String,

)