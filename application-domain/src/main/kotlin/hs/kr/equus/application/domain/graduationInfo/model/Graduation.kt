package hs.kr.equus.application.domain.graduationInfo.model

import hs.kr.equus.application.domain.graduationInfo.model.vo.StudentNumber
import hs.kr.equus.application.global.annotation.domain.Aggregate
import java.time.LocalDate
import java.util.UUID

@Aggregate
class Graduation(
    val receiptCode: UUID,
    val isGraduated: Boolean,
    val graduatedAt: LocalDate,
    val studentNumber: StudentNumber,
    val schoolTel: String,
    val schoolId: UUID,
) : GraduationInfo(receiptCode)
