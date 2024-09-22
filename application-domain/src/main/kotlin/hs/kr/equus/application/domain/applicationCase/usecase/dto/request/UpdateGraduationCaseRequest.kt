package hs.kr.equus.application.domain.applicationCase.usecase.dto.request

import hs.kr.equus.application.domain.applicationCase.model.GraduationCase

data class UpdateGraduationCaseRequest(
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
    val extraScore: ExtraScoreRequest
) {
    companion object {
        fun of(graduationCase: GraduationCase): UpdateGraduationCaseRequest {
            return UpdateGraduationCaseRequest(
                volunteerTime = graduationCase.volunteerTime,
                absenceDayCount = graduationCase.absenceDayCount,
                lectureAbsenceCount = graduationCase.lectureAbsenceCount,
                latenessCount = graduationCase.latenessCount,
                earlyLeaveCount = graduationCase.earlyLeaveCount,
                koreanGrade = graduationCase.koreanGrade,
                socialGrade = graduationCase.socialGrade,
                historyGrade = graduationCase.historyGrade,
                mathGrade = graduationCase.mathGrade,
                scienceGrade = graduationCase.scienceGrade,
                englishGrade = graduationCase.englishGrade,
                techAndHomeGrade = graduationCase.techAndHomeGrade,
                extraScore = ExtraScoreRequest(
                    hasCertificate = graduationCase.extraScoreItem.hasCertificate,
                    hasCompetitionPrize = graduationCase.extraScoreItem.hasCompetitionPrize
                )
            )
        }
    }
}

