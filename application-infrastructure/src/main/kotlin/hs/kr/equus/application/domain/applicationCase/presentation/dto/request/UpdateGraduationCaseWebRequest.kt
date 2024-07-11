package hs.kr.equus.application.domain.applicationCase.presentation.dto.request

import hs.kr.equus.application.domain.applicationCase.usecase.dto.request.ExtraScoreRequest
import javax.validation.constraints.Pattern
import javax.validation.constraints.PositiveOrZero

data class UpdateGraduationCaseWebRequest(
    @PositiveOrZero
    val volunteerTime: Int,
    @PositiveOrZero
    val absenceDayCount: Int,
    @PositiveOrZero
    val lectureAbsenceCount: Int,
    @PositiveOrZero
    val latenessCount: Int,
    @PositiveOrZero
    val earlyLeaveCount: Int,
    @Pattern(regexp = "[A-E,X]{4}")
    val koreanGrade: String,
    @Pattern(regexp = "[A-E,X]{4}")
    val socialGrade: String,
    @Pattern(regexp = "[A-E,X]{4}")
    val historyGrade: String,
    @Pattern(regexp = "[A-E,X]{4}")
    val mathGrade: String,
    @Pattern(regexp = "[A-E,X]{4}")
    val scienceGrade: String,
    @Pattern(regexp = "[A-E,X]{4}")
    val englishGrade: String,
    @Pattern(regexp = "[A-E,X]{4}")
    val techAndHomeGrade: String,
    val extraScore: ExtraScoreRequest
)
