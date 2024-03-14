package esperer.concurrency.user

import org.springframework.data.relational.core.mapping.Table
import java.util.UUID

@Table("tbl_user")
class User(

    val id: Long,

    val name: String,

    val password: String,


)