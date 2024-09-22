package hs.kr.equus.application.domain.application.spi

import hs.kr.equus.application.domain.application.usecase.dto.vo.ApplicationInfoVO
import javax.servlet.http.HttpServletResponse

interface PrintApplicationInfoPort {
    fun execute(httpServletResponse: HttpServletResponse, applicationInfoVO: List<ApplicationInfoVO>)
}