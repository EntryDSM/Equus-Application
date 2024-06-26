package hs.kr.equus.application.domain.application.domain

import com.querydsl.jpa.impl.JPAQueryFactory
import hs.kr.equus.application.domain.application.domain.entity.QApplicationJpaEntity.applicationJpaEntity
import hs.kr.equus.application.domain.application.domain.mapper.ApplicationMapper
import hs.kr.equus.application.domain.application.usecase.dto.response.ApplicantCodeResponse
import hs.kr.equus.application.domain.application.domain.repository.ApplicationJpaRepository
import hs.kr.equus.application.domain.application.model.Application
import hs.kr.equus.application.domain.application.model.types.ApplicationType
import hs.kr.equus.application.domain.application.spi.ApplicationPort
import hs.kr.equus.application.domain.application.usecase.dto.response.GetApplicationCountResponse
import hs.kr.equus.application.global.feign.client.StatusClient
import hs.kr.equus.application.global.feign.client.dto.response.StatusInfoElement
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class ApplicationPersistenceAdapter(
    private val applicationMapper: ApplicationMapper,
    private val applicationJpaRepository: ApplicationJpaRepository,
    private val jpaQueryFactory: JPAQueryFactory,
    private val statusClient: StatusClient,
) : ApplicationPort {
    override fun save(application: Application): Application {
        return applicationJpaRepository.save(
            applicationMapper.toEntity(application),
        ).let(applicationMapper::toDomainNotNull)
    }

    override fun queryApplicationByUserId(userId: UUID): Application? {
        return applicationJpaRepository.findByUserId(userId)
            .let(applicationMapper::toDomain)
    }

    override fun isExistsApplicationByUserId(userId: UUID): Boolean {
        return applicationJpaRepository.existsByUserId(userId)
    }

    override fun queryReceiptCodeByUserId(userId: UUID): Long? {
        return applicationJpaRepository.findReceiptCodeByUserId(userId)
    }

    override fun queryApplicationByReceiptCode(receiptCode: Long): Application? {
        return applicationJpaRepository.findByReceiptCode(receiptCode)
            .let(applicationMapper::toDomain)
    }

    override fun queryApplicationCountByApplicationTypeAndIsDaejeon(
        applicationType: ApplicationType,
        isDaejeon: Boolean,
    ): GetApplicationCountResponse {

        val statusMap: Map<Long, StatusInfoElement> =
            statusClient.getStatusList()
                .associateBy(StatusInfoElement::receiptCode)

        val count = jpaQueryFactory.selectFrom(applicationJpaEntity)
            .where(
                applicationJpaEntity.applicationType.eq(applicationType)
                    .and(applicationJpaEntity.isDaejeon.eq(isDaejeon))
            ).fetch().count {
                val status = statusMap[it.receiptCode]
                status != null && status.isFirstRoundPass
            }

        return GetApplicationCountResponse(
            applicationType,
            isDaejeon,
            count,
        )
    }
    override fun queryApplicantCodesByIsFirstRoundPass(): List<ApplicantCodeResponse> {
        val statusMap = statusClient.getStatusList().associateBy(StatusInfoElement::receiptCode)

        return jpaQueryFactory
            .select(
                applicationJpaEntity
            )
            .from(applicationJpaEntity)
            .where(
                applicationJpaEntity.receiptCode.`in`(statusMap.keys.toList()),
            )
            .fetch()
            .filter { statusMap[it.receiptCode]?.isFirstRoundPass == true }
            .map { it ->
                val examCode = statusMap[it.receiptCode]?.examCode ?: ""
                ApplicantCodeResponse(it.receiptCode, examCode, it.applicantName!!)
            }
    }
}
