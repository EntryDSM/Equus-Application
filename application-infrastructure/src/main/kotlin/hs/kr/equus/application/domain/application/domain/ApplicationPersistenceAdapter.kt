package hs.kr.equus.application.domain.application.domain

import com.querydsl.jpa.impl.JPAQueryFactory
import hs.kr.equus.application.domain.application.domain.entity.QApplicationJpaEntity.applicationJpaEntity
import hs.kr.equus.application.domain.application.domain.mapper.ApplicationMapper
import hs.kr.equus.application.domain.application.domain.repository.ApplicationJpaRepository
import hs.kr.equus.application.domain.application.model.Applicant
import hs.kr.equus.application.domain.application.model.Application
import hs.kr.equus.application.domain.application.model.types.ApplicationType
import hs.kr.equus.application.domain.application.spi.ApplicationPort
import hs.kr.equus.application.domain.application.usecase.dto.response.GetApplicantsResponse
import hs.kr.equus.application.domain.application.usecase.dto.response.GetApplicationCountResponse
import hs.kr.equus.application.domain.graduationInfo.domain.entity.QGraduationJpaEntity.graduationJpaEntity
import hs.kr.equus.application.domain.graduationInfo.domain.entity.QQualificationJpaEntity.qualificationJpaEntity
import hs.kr.equus.application.domain.score.exception.ScoreExceptions
import hs.kr.equus.application.domain.status.exception.StatusExceptions
import hs.kr.equus.application.global.feign.client.StatusClient
import hs.kr.equus.application.global.feign.client.dto.response.StatusInfoElement
import org.springframework.data.domain.PageImpl
import org.springframework.stereotype.Component
import java.awt.print.Pageable
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

    override fun queryAllApplicantsByFilter(
        schoolName: String,
        name: String,
        isDaejeon: Boolean?,
        isOutOfHeadcount: Boolean?,
        isCommon: Boolean,
        isMeister: Boolean,
        isSocial: Boolean,
        isSubmitted: Boolean?,
        pageSize: Long,
        offset: Long
    ): List<Applicant> {
        val statusMap: Map<Long, StatusInfoElement> =
            statusClient.getStatusList()
                .associateBy(StatusInfoElement::receiptCode)

        val query = jpaQueryFactory
            .selectFrom(applicationJpaEntity)
            .leftJoin(qualificationJpaEntity).on(applicationJpaEntity.receiptCode.eq(qualificationJpaEntity.receiptCode))
            .leftJoin(graduationJpaEntity).on(applicationJpaEntity.receiptCode.eq(graduationJpaEntity.receiptCode))
            .where(
                applicationJpaEntity.applicantName.contains(name),
                isDaejeon?.let { applicationJpaEntity.isDaejeon.eq(it) },
                isOutOfHeadcount?.let { applicationJpaEntity.isOutOfHeadcount.eq(it) },
                applicationJpaEntity.applicationType.`in`(getApplicationTypes(isCommon, isMeister, isSocial))
            )
            .orderBy(applicationJpaEntity.receiptCode.asc());

        val applications = query.limit(pageSize).offset(offset).fetch()

        // isSubmitted 가 null 이 아닌 경우에만 filter 를 거침
        val applicants = isSubmitted?.let { submitted ->
            applications.filter { application ->
                statusMap[application.receiptCode]?.isSubmitted == submitted
            }
        } ?: applications

        val result = applicants.map { application ->
            val status = statusMap[application.receiptCode] ?: throw StatusExceptions.StatusNotFoundException()
            Applicant(
                receiptCode = application.receiptCode,
                name = application.applicantName,
                telephoneNumber = application.applicantTel,
                isDaejeon = application.isDaejeon,
                isPrintsArrived = status.isPrintsArrived,
                applicationType = application.applicationType?.name,
                isSubmitted = status.isSubmitted,
                isOutOfHeadcount = application.isOutOfHeadcount
            )
        }

        return result
    }

    private fun getApplicationTypes(isCommon: Boolean?, isMeister: Boolean?, isSocial: Boolean?): List<ApplicationType> {
        val applicationTypes = mutableListOf<ApplicationType>()
        if (isCommon == true) applicationTypes.add(ApplicationType.COMMON)
        if (isMeister == true) applicationTypes.add(ApplicationType.MEISTER)
        if (isSocial == true) applicationTypes.add(ApplicationType.SOCIAL)
        return applicationTypes
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
}
