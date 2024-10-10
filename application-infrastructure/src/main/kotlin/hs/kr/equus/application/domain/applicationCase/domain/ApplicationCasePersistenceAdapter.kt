package hs.kr.equus.application.domain.applicationCase.domain

import hs.kr.equus.application.domain.application.model.Application
import hs.kr.equus.application.domain.application.model.types.EducationalStatus
import hs.kr.equus.application.domain.applicationCase.domain.mapper.GraduationCaseMapper
import hs.kr.equus.application.domain.applicationCase.domain.mapper.QualificationCaseMapper
import hs.kr.equus.application.domain.applicationCase.domain.repository.GraduationCaseJpaRepository
import hs.kr.equus.application.domain.applicationCase.domain.repository.QualificationCaseJpaRepository
import hs.kr.equus.application.domain.applicationCase.model.ApplicationCase
import hs.kr.equus.application.domain.applicationCase.model.GraduationCase
import hs.kr.equus.application.domain.applicationCase.model.QualificationCase
import hs.kr.equus.application.domain.applicationCase.spi.ApplicationCasePort
import org.springframework.stereotype.Component

@Component
class ApplicationCasePersistenceAdapter(
    private val graduationCaseJpaRepository: GraduationCaseJpaRepository,
    private val qualificationCaseJpaRepository: QualificationCaseJpaRepository,
    private val graduationCaseMapper: GraduationCaseMapper,
    private val qualificationCaseMapper: QualificationCaseMapper,
) : ApplicationCasePort {
    override fun save(applicationCase: ApplicationCase): ApplicationCase {
        return when (applicationCase) {
            is GraduationCase -> {
                graduationCaseJpaRepository.save(graduationCaseMapper.toEntity(applicationCase))
                    .let(graduationCaseMapper::toDomainNotNull)
            }

            is QualificationCase -> {
                qualificationCaseJpaRepository.save(qualificationCaseMapper.toEntity(applicationCase))
                    .let(qualificationCaseMapper::toDomainNotNull)
            }
        }
    }

    override fun delete(applicationCase: ApplicationCase) {
        when (applicationCase) {
            is GraduationCase -> {
                graduationCaseJpaRepository.delete(graduationCaseMapper.toEntity(applicationCase))
            }

            is QualificationCase -> {
                qualificationCaseJpaRepository.delete(qualificationCaseMapper.toEntity(applicationCase))
            }
        }
    }

    override fun queryApplicationCaseByApplication(application: Application): ApplicationCase? {
        application.run {
            return if (educationalStatus == EducationalStatus.QUALIFICATION_EXAM) {
                qualificationCaseJpaRepository.findByReceiptCode(receiptCode)
                    .let(qualificationCaseMapper::toDomain)
            } else {
                graduationCaseJpaRepository.findByReceiptCode(receiptCode)
                    .let(graduationCaseMapper::toDomain)
            }
        }
    }

    override fun isExistsApplicationCaseByApplication(application: Application): Boolean {
        application.run {
            return if (educationalStatus == EducationalStatus.QUALIFICATION_EXAM) {
                qualificationCaseJpaRepository.existsByReceiptCode(receiptCode)
            } else {
                graduationCaseJpaRepository.existsByReceiptCode(receiptCode)
            }
        }
    }

    override fun queryAllApplicationCaseByReceiptCode(receiptCodeList: List<Long>): List<ApplicationCase?> {
        val graduationCaseList = graduationCaseJpaRepository.findAllByReceiptCodeIn(receiptCodeList)
            .map { graduationCaseMapper.toDomain(it) }
            .associateBy { it?.receiptCode }

        val qualificationCaseList = qualificationCaseJpaRepository.findAllByReceiptCodeIn(receiptCodeList)
            .map { qualificationCaseMapper.toDomain(it) }
            .associateBy { it?.receiptCode }

        return receiptCodeList.map { receiptCode ->
            graduationCaseList[receiptCode] ?: qualificationCaseList[receiptCode]
        }
    }


}