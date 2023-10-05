package hs.kr.equus.application.domain.score.model

import hs.kr.equus.application.domain.score.model.vo.SubjectGrade
import hs.kr.equus.application.global.annotation.domain.Aggregate

@Aggregate
data class GraduationCase(
    val volunteerTime: Int,
    val AbsenceDayCount: Int,
    val lectureAbsenceCount: Int,
    val latenessCount: Int,
    val earlyLeaveCount: Int,
    val subjectGrade: SubjectGrade,
    val receiptCode: Long,
) : ApplicationCase(receiptCode)
