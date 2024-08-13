package hs.kr.equus.application.domain.application.spi

import hs.kr.equus.application.domain.application.model.Application

interface QueryApplicationInfoListByStatusIsSubmittedTruePort {
    fun queryApplicationInfoListByStatusIsSubmittedTrue(): List<Application>
}