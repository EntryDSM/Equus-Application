package hs.kr.equus.application.domain.graduationInfo.model

import hs.kr.equus.application.global.annotation.domain.Aggregate
import java.time.LocalDate

@Aggregate
data class Qualification(
    val receiptCode: Long,
    val qualifiedAt: LocalDate,
) : GraduationInfo(receiptCode)
