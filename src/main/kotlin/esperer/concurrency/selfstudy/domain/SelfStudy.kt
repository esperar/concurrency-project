package esperer.concurrency.selfstudy.domain

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("tbl_selfstudy")
class SelfStudy(
    @field:Id
    val id: Long = 0,

    var roomCount: Int,

    val limitCount: Int,
) {
    fun plusRoomCount() {
        this.roomCount++
    }
}