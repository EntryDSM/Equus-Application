package hs.kr.equus.application.domain.graduationInfo.model

import hs.kr.equus.application.domain.graduationInfo.model.vo.StudentNumber
import hs.kr.equus.application.global.annotation.domain.Aggregate
import java.time.LocalDate
import java.util.UUID

@Aggregate
data class Graduation(
    val isGraduated: Boolean,
    val graduatedAt: LocalDate,
    val studentNumber: StudentNumber,
    val schoolTel: String,
    val schoolId: UUID,
    val receiptCode: Long,
) : GraduationInfo(receiptCode)
