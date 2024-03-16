package esperer.concurrency.user

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("tbl_user")
class User(
    @field:Id
    val id: Long,

    val name: String,

    val password: String,

    selfStudyId: Long?
) {
    var selfStudyId = selfStudyId
        private set

    fun reserveSelfStudy(selfStudyId: Long) {
        this.selfStudyId = selfStudyId
    }
}