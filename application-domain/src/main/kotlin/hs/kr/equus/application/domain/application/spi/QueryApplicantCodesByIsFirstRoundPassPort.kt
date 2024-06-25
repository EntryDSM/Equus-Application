package hs.kr.equus.application.domain.application.spi

import hs.kr.equus.application.domain.application.usecase.dto.response.ApplicantCodeResponse

interface QueryApplicantCodesByIsFirstRoundPassPort {
    fun queryApplicantCodesByIsFirstRoundPass(): List<ApplicantCodeResponse>
}