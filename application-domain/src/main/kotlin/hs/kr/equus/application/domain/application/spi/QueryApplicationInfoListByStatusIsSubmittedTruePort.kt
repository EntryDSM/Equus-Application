package hs.kr.equus.application.domain.application.spi

import hs.kr.equus.application.domain.application.model.Application
import hs.kr.equus.application.domain.application.usecase.dto.vo.ApplicationInfoVO

interface QueryApplicationInfoListByStatusIsSubmittedTruePort {
    fun queryApplicationInfoListByStatusIsSubmittedTrue(): List<ApplicationInfoVO>
}