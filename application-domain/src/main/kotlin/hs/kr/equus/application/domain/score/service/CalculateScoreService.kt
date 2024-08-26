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
    fun calculateScore(applicationCase: ApplicationCase, isCommon: Boolean): BigDecimal {
        val competitionPrize = if (applicationCase.extraScoreItem.hasCompetitionPrize) BigDecimal(3) else BigDecimal.ZERO
        val certificate = if (applicationCase.extraScoreItem.hasCertificate) BigDecimal(6) else BigDecimal.ZERO
        return if (isCommon) certificate else competitionPrize + certificate
    }
}