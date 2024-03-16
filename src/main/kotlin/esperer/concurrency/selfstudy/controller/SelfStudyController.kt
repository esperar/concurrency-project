package esperer.concurrency.selfstudy.controller

import esperer.concurrency.selfstudy.dto.CreateSelfStudyRequest
import esperer.concurrency.selfstudy.dto.SelfStudyResponse
import esperer.concurrency.selfstudy.service.SelfStudyService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.net.URI

@RestController
@RequestMapping("/selfstudy")
class SelfStudyController(
    private val service: SelfStudyService
) {

    @PostMapping("/{id}")
    fun reserve(@PathVariable id: Long, @RequestBody request: CreateSelfStudyRequest): Mono<ResponseEntity<SelfStudyResponse>> {
        return service.reserve(id, request)
            .map { ResponseEntity.created(URI.create("/selfstudy")).body(it) }
    }
}