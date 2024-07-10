package hs.kr.equus.application.domain.score.service

import hs.kr.equus.application.domain.application.model.Application
import hs.kr.equus.application.domain.application.model.types.ApplicationType
import hs.kr.equus.application.domain.applicationCase.model.ApplicationCase
import hs.kr.equus.application.domain.applicationCase.model.GraduationCase
import hs.kr.equus.application.domain.applicationCase.model.QualificationCase
import hs.kr.equus.application.global.annotation.DomainService
import java.math.BigDecimal

@DomainService
class CalculateExtraScoreService{
    companion object {
        private val COMPETITION_PRIZE_EXTRA_SCORE = BigDecimal(3)
        private val CERTIFICATE_EXTRA_SCORE = BigDecimal(6)
        private val COMMON_TYPE_MAX_EXTRA_SCORE = BigDecimal(3)
        private val SPECIAL_TYPE_MAX_EXTRA_SCORE = BigDecimal(9)
    }
    fun calculateScore(application: Application, applicationCase: ApplicationCase): BigDecimal {
        if(applicationCase is QualificationCase) {
            return BigDecimal.ZERO
        }
        val graduationCase = applicationCase as GraduationCase
        val applicationType = application.applicationType ?: return BigDecimal.ZERO

        return when (applicationType) {
            ApplicationType.COMMON -> {
                val score = if (graduationCase.extraScoreItem.hasCompetitionPrize) {
                    COMPETITION_PRIZE_EXTRA_SCORE
                } else {
                    BigDecimal.ZERO
                }
                score.min(COMMON_TYPE_MAX_EXTRA_SCORE)
            }
            ApplicationType.SOCIAL, ApplicationType.MEISTER -> {
                val score = listOf(
                    graduationCase.extraScoreItem.hasCertificate to CERTIFICATE_EXTRA_SCORE,
                    graduationCase.extraScoreItem.hasCompetitionPrize to COMPETITION_PRIZE_EXTRA_SCORE
                ).filter { it.first }
                    .sumOf { it.second }
                score.min(SPECIAL_TYPE_MAX_EXTRA_SCORE)
            }
        }
    }
}