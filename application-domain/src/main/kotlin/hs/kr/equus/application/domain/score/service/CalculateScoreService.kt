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
    fun calculateScore(application: Application, applicationCase: ApplicationCase): BigDecimal {
        if(applicationCase is QualificationCase) {
            return BigDecimal.ZERO
        }
        val graduationCase = applicationCase as GraduationCase
        val applicationType = application.applicationType ?: return BigDecimal.ZERO

        return when (applicationType) {
            ApplicationType.COMMON -> if (graduationCase.extraScoreItem.hasCompetitionPrize) {
                BigDecimal(3)
            } else {
                BigDecimal.ZERO
            }
            ApplicationType.SOCIAL, ApplicationType.MEISTER -> listOf(
                graduationCase.extraScoreItem.hasCertificate to BigDecimal(6),
                graduationCase.extraScoreItem.hasCompetitionPrize to BigDecimal(3)
            ).filter { it.first }
                .sumOf { it.second }
        }
    }
}