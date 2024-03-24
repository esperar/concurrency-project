package esperer.concurrency.selfstudy.service

import esperer.concurrency.selfstudy.domain.SelfStudy
import esperer.concurrency.selfstudy.domain.SelfStudyRepository
import esperer.concurrency.selfstudy.domain.SelfStudyUserRepository
import esperer.concurrency.selfstudy.dto.CreateSelfStudyRequest
import esperer.concurrency.user.domain.User
import esperer.concurrency.user.domain.UserRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.MethodOrderer
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestMethodOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

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
            limitCount = 50
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

    // @Test
    fun `락_사용_회원_50명이_자습신청`() {
        userList.parallelStream().forEach {
            selfStudyService.reserve(2, CreateSelfStudyRequest(it.id))
        }
        val selfStudyUserCount = selfStudyUserRepository.findAll().count()
        Assertions.assertThat(selfStudyUserCount.block()).isEqualTo(50L)
    }
}