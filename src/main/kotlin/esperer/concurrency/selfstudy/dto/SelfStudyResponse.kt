package esperer.concurrency.selfstudy.dto

data class SelfStudyResponse(
    val selfStudyId: Long,
    val userId: Long,
    val roomCount: Int,
    val limit: Int
)
