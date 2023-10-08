package hs.kr.equus.application.domain.graduationInfo.model

import hs.kr.equus.application.domain.application.model.types.EducationalStatus
import hs.kr.equus.application.domain.graduationInfo.model.vo.StudentNumber
import hs.kr.equus.application.global.annotation.Aggregate
import java.time.LocalDate
import java.util.*

@Aggregate
data class GraduationInfo(
    val id: UUID = UUID(0, 0),
    val educationalStatus: EducationalStatus,
    val graduateDate: LocalDate,
    val studentNumber: StudentNumber?,
    val schoolTel: String?,
    val schoolId: UUID?,
    val receiptCode: Long,
)
