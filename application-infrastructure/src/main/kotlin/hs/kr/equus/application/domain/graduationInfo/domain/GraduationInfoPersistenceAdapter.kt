package hs.kr.equus.application.domain.graduationInfo.domain

import hs.kr.equus.application.domain.application.model.Application
import hs.kr.equus.application.domain.application.model.types.EducationalStatus
import hs.kr.equus.application.domain.graduationInfo.domain.mapper.GraduationMapper
import hs.kr.equus.application.domain.graduationInfo.domain.mapper.QualificationMapper
import hs.kr.equus.application.domain.graduationInfo.domain.repository.GraduationJpaRepository
import hs.kr.equus.application.domain.graduationInfo.domain.repository.QualificationJpaRepository
import hs.kr.equus.application.domain.graduationInfo.model.Graduation
import hs.kr.equus.application.domain.graduationInfo.model.GraduationInfo
import hs.kr.equus.application.domain.graduationInfo.model.Qualification
import hs.kr.equus.application.domain.graduationInfo.spi.GraduationInfoPort
import org.springframework.stereotype.Component
import java.lang.IllegalArgumentException

@Component
class GraduationInfoPersistenceAdapter(
    private val graduationMapper: GraduationMapper,
    private val qualificationMapper: QualificationMapper,
    private val graduationJpaRepository: GraduationJpaRepository,
    private val qualificationJpaRepository: QualificationJpaRepository,
) : GraduationInfoPort {
    override fun save(graduationInfo: GraduationInfo): GraduationInfo {
        return when (graduationInfo) {
            is Qualification -> {
                qualificationJpaRepository.save(qualificationMapper.toEntity(graduationInfo))
                    .let(qualificationMapper::toDomainNotNull)
            }

            is Graduation -> {
                graduationJpaRepository.save(graduationMapper.toEntity(graduationInfo))
                    .let(graduationMapper::toDomainNotNull)
            }

            else -> throw IllegalArgumentException()
        }
    }

    override fun queryGraduationInfoByApplication(application: Application): GraduationInfo? {
        application.run {
            return if (educationalStatus == EducationalStatus.QUALIFICATION_EXAM) {
                qualificationJpaRepository.findByReceiptCode(receiptCode)
                    .let(qualificationMapper::toDomain)
            } else {
                graduationJpaRepository.findByReceiptCode(receiptCode)
                    .let(graduationMapper::toDomain)
            }
        }
    }

    override fun delete(graduationInfo: GraduationInfo) {
        when (graduationInfo) {
            is Qualification -> {
                qualificationJpaRepository.delete(qualificationMapper.toEntity(graduationInfo))
            }

            is Graduation -> {
                graduationJpaRepository.delete(graduationMapper.toEntity(graduationInfo))
            }
        }
    }
}