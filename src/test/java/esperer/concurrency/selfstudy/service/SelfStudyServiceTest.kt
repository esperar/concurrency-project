package esperer.concurrency.selfstudy.service

import esperer.concurrency.selfstudy.domain.SelfStudy
import esperer.concurrency.user.domain.User
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@SpringBootTest
class SelfStudyServiceTest(
    @Autowired
    private val selfStudyService: SelfStudyService
) {

    private val selfStudyStub by lazy {
        SelfStudy(
            id = 1,
            userId
        )
    }

    @BeforeEach
    fun beforeEach() {

    }

    fun `락_사용_회원_50명이_자습신청`() {

    }
}