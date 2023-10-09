package hs.kr.equus.application.domain.application.service

import hs.kr.equus.application.domain.application.spi.ApplicationQueryUserPort
import hs.kr.equus.application.domain.user.model.User
import hs.kr.equus.application.global.annotation.EquusTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.springframework.boot.test.mock.mockito.MockBean
import java.util.UUID

@EquusTest
class CheckTelServiceTest {
    @MockBean
    private lateinit var applicationQueryUserPort: ApplicationQueryUserPort

    private lateinit var checkTelService: CheckTelService

    private val userId = UUID.randomUUID()

    private val phoneNumber = "010-1234-5678"

    private val password = "test1234!"

    private val name = "test"

    @BeforeEach
    fun setUp() {
        checkTelService =
            CheckTelService(
                applicationQueryUserPort,
            )
    }

    @Test
    fun `학부모일 경우`() {
        val user =
            User(
                id = userId,
                isParent = true,
                phoneNumber = phoneNumber,
                password = password,
                name = name,
            )

        given(applicationQueryUserPort.queryUserByUserId(userId))
            .willReturn(user)

        assert(
            checkTelService.checkParentPutApplicantTel(userId, phoneNumber) == "010-0000-0000",
        )
    }

    @Test
    fun `학생일 경우`() {
        val user =
            User(
                id = userId,
                isParent = false,
                phoneNumber = phoneNumber,
                password = password,
                name = name,
            )

        given(applicationQueryUserPort.queryUserByUserId(userId))
            .willReturn(user)

        assert(
            checkTelService.checkParentPutApplicantTel(userId, phoneNumber) == phoneNumber,
        )
    }
}
