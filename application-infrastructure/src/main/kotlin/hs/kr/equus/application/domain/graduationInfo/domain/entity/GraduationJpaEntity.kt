package hs.kr.equus.application.domain.graduationInfo.domain.entity

import hs.kr.equus.application.domain.graduationInfo.domain.entity.vo.StudentNumber
import java.time.YearMonth
import javax.persistence.Column
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "tbl_graduation")
class GraduationJpaEntity(
    override val id: Long,
    override val graduateDate: YearMonth?,
    override val isProspectiveGraduate: Boolean,
    override val receiptCode: Long,
    @Embedded
    val studentNumber: StudentNumber?,
    val schoolCode: String?,
    val teacherName: String?,
    @Column(nullable = true)
    val teacherTel: String?
) : GraduationInfoEntity(
    id = id,
    graduateDate = graduateDate,
    isProspectiveGraduate = isProspectiveGraduate,
    receiptCode = receiptCode,
)
