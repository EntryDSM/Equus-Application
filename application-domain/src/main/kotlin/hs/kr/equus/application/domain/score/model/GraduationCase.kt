package hs.kr.equus.application.domain.score.model

import hs.kr.equus.application.domain.score.model.vo.SubjectGrade
import hs.kr.equus.application.global.annotation.domain.Aggregate
import java.util.*

@Aggregate
data class GraduationCase(
    val id: UUID,
    val volunteerTime: Int,
    val AbsenceDayCount: Int,
    val lectureAbsenceCount: Int,
    val latenessCount: Int,
    val earlyLeaveCount: Int,
    val subjectGrade: SubjectGrade,
    val receiptCode: UUID,
) : ApplicationCase(id, receiptCode)
