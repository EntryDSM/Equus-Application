package hs.kr.equus.application.domain.applicationCase.usecase.dto.response

data class GetGraduationCaseResponse(
    val volunteerTime: Int,
    val absenceDayCount: Int,
    val lectureAbsenceCount: Int,
    val latenessCount: Int,
    val earlyLeaveCount: Int,
    val koreanGrade: String,
    val socialGrade: String,
    val historyGrade: String,
    val mathGrade: String,
    val scienceGrade: String,
    val englishGrade: String,
    val techAndHomeGrade: String,
)