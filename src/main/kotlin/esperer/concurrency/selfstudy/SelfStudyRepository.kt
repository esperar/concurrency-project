package esperer.concurrency.selfstudy

import org.springframework.data.r2dbc.repository.R2dbcRepository

interface SelfStudyRepository : R2dbcRepository<SelfStudy, Long>