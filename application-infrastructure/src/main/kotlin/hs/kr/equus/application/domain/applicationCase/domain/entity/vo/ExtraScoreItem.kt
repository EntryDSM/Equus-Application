package hs.kr.equus.application.domain.applicationCase.domain.entity.vo

import javax.persistence.Embeddable

@Embeddable
data class ExtraScoreItem(
    val hasCertificate: Boolean,
    val hasCompetitionPrize: Boolean
)