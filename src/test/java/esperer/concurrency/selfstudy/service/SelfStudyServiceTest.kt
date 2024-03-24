package esperer.concurrency.selfstudy.service

import esperer.concurrency.selfstudy.domain.SelfStudy
import esperer.concurrency.selfstudy.domain.SelfStudyRepository
import esperer.concurrency.selfstudy.domain.SelfStudyUserRepository
import esperer.concurrency.selfstudy.dto.CreateSelfStudyRequest
import esperer.concurrency.user.domain.User
import esperer.concurrency.user.domain.UserRepository
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono

@TestMethodOrder(MethodOrderer.OrderAnnotation::class)
@SpringBootTest
class SelfStudyServiceTest(
    @Autowired
    private val userRepository: UserRepository,
    @Autowired
    private val selfStudyRepository: SelfStudyRepository,
    @Autowired
    private val selfStudyUserRepository: SelfStudyUserRepository
) {

    private lateinit var selfStudyService: SelfStudyService

    val userList = mutableListOf<User>()

    private val selfStudyStub by lazy {
        SelfStudy(
            id = 2,
            roomCount = 0,
            limit = 50
        )
    }

    @BeforeEach
    fun beforeEach() {
        // service set
        selfStudyService = SelfStudyService(selfStudyRepository, userRepository, selfStudyUserRepository)
        // user set
        val name = "test_name_"
        val password = "testpassword"
        for(i in 2 until 52) {
            val user = User(
                id = i.toLong(),
                name = "${name}$i",
                password = password
            )
            userRepository.save(user)
            userList.add(user)
        }

        // selfStudy set
        selfStudyRepository.save(selfStudyStub)
    }

    @Test
    @Transactional
    fun `락_사용_회원_50명이_자습신청`() {
        userList.parallelStream().forEach {
            selfStudyService.reserve(2, CreateSelfStudyRequest(it.id))
        }
        val selfStudyUserCount = selfStudyUserRepository.findAll().count()
        assertTrue(selfStudyUserCount == Mono.just(50L))
    }
}