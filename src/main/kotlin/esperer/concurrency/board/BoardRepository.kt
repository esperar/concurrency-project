package esperer.concurrency.board

import org.springframework.data.r2dbc.repository.R2dbcRepository

interface BoardRepository : R2dbcRepository<Board, Long>