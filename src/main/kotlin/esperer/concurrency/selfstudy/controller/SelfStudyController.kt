package esperer.concurrency.selfstudy.controller

import esperer.concurrency.selfstudy.dto.CreateSelfStudyRequest
import esperer.concurrency.selfstudy.service.SelfStudyService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/selfstudy")
class SelfStudyController(
    private val service: SelfStudyService
) {

    @PostMapping("/{id}")
    fun reserve(@PathVariable id: Long, @RequestBody request: CreateSelfStudyRequest): ResponseEntity<Void> {
        service.reserve(id, request)
        return ResponseEntity.created(URI.create("/selfstudy")).build()
    }
}