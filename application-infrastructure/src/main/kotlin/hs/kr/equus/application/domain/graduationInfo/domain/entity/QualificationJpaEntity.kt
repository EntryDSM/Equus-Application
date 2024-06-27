package hs.kr.equus.application.domain.graduationInfo.domain.entity

import java.time.YearMonth
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "tbl_qualification")
class QualificationJpaEntity(
    override val id: Long,
    override val isProspectiveGraduate: Boolean,
    override val receiptCode: Long,
    override val graduateDate: YearMonth?,
) : GraduationInfoEntity(
    id = id,
    graduateDate = graduateDate,
    isProspectiveGraduate = isProspectiveGraduate,
    receiptCode = receiptCode,
)
