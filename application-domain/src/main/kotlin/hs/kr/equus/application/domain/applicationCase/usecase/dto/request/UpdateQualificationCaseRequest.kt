package hs.kr.equus.application.domain.applicationCase.usecase.dto.request

import hs.kr.equus.application.domain.applicationCase.model.QualificationCase
import java.math.BigDecimal

data class UpdateQualificationCaseRequest(
    val koreanGrade: BigDecimal = BigDecimal.ZERO,
    val socialGrade: BigDecimal = BigDecimal.ZERO,
    val mathGrade: BigDecimal = BigDecimal.ZERO,
    val scienceGrade: BigDecimal = BigDecimal.ZERO,
    val englishGrade: BigDecimal = BigDecimal.ZERO,
    val optGrade: BigDecimal = BigDecimal.ZERO,
    val extraScore: ExtraScoreRequest
) {
    companion object {
        fun of(qualificationCase: QualificationCase): UpdateQualificationCaseRequest {
            return UpdateQualificationCaseRequest(
                koreanGrade = qualificationCase.koreanGrade,
                socialGrade = qualificationCase.socialGrade,
                mathGrade = qualificationCase.mathGrade,
                scienceGrade = qualificationCase.scienceGrade,
                englishGrade = qualificationCase.englishGrade,
                optGrade = qualificationCase.optGrade,
                extraScore = ExtraScoreRequest(
                    hasCertificate = qualificationCase.extraScoreItem.hasCertificate,
                    hasCompetitionPrize = qualificationCase.extraScoreItem.hasCompetitionPrize
                )
            )
        }
    }
}
