package esperer.concurrency.selfstudy.service

import esperer.concurrency.selfstudy.domain.SelfStudyRepository
import esperer.concurrency.selfstudy.dto.CreateSelfStudyRequest
import org.springframework.stereotype.Service

@Service
class SelfStudyService(
    private val selfStudyRepository: SelfStudyRepository
) {

    fun reserve(id: Long, request: CreateSelfStudyRequest){

    }
}