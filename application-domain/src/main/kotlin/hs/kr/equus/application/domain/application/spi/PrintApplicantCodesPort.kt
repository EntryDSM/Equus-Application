package hs.kr.equus.application.domain.application.spi

import hs.kr.equus.application.domain.application.usecase.dto.vo.ApplicationCodeVO
import javax.servlet.http.HttpServletResponse

interface PrintApplicantCodesPort {
    fun execute(response: HttpServletResponse, applicationCodeVO: List<ApplicationCodeVO>)
}