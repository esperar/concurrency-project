package esperer.concurrency.selfstudy.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("tbl_selfstudy")
class SelfStudy(
    @field:Id
    val id: Long,

    val userId: Long,

    var roomCount: Int,

    val limit: Int,
) {
    fun plusRoomCount() {
        this.roomCount++
    }
}