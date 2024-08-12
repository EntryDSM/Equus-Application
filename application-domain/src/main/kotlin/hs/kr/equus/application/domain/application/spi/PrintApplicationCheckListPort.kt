package hs.kr.equus.application.domain.application.spi

import hs.kr.equus.application.domain.application.usecase.dto.vo.ApplicationCheckListVO
import javax.servlet.http.HttpServletResponse

interface PrintApplicationCheckListPort {
    fun execute(httpServletResponse: HttpServletResponse, applicationCheckListVO: List<ApplicationCheckListVO>)
}