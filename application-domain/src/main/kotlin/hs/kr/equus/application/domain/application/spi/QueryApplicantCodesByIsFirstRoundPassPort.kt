package hs.kr.equus.application.domain.application.spi

import hs.kr.equus.application.domain.application.usecase.dto.vo.ApplicationCodeVO


interface QueryApplicantCodesByIsFirstRoundPassPort {
    fun queryApplicantCodesByIsFirstRoundPass(): List<ApplicationCodeVO>
}