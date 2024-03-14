package esperer.concurrency.board

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("tbl_board")
class Board(
    @field:Id
    val id: Long,

    val userId: Long,

    title: String,

    content: String,
) {
    var title = title
        private set

    var content =content
        private set
}