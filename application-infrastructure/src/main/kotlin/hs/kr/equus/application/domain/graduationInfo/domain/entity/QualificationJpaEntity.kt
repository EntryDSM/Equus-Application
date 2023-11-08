package hs.kr.equus.application.domain.graduationInfo.domain.entity

import java.time.LocalDate
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "tbl_qualification")
class QualificationJpaEntity(
    override val id: Long?,
    override val isProspectiveGraduate: Boolean,
    override val receiptCode: Long,
    qualifiedDate: LocalDate,
) : GraduationInfoEntity(
    id = id,
    graduateDate = qualifiedDate,
    isProspectiveGraduate = isProspectiveGraduate,
    receiptCode = receiptCode,
)
