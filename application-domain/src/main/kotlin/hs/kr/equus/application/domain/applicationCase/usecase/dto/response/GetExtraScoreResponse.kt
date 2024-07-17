package hs.kr.equus.application.domain.applicationCase.usecase.dto.response

data class GetExtraScoreResponse(
    val hasCertificate: Boolean,
    val hasCompetitionPrize: Boolean
)
