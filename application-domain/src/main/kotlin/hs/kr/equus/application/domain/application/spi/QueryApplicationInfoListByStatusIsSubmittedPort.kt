package hs.kr.equus.application.domain.application.spi

import hs.kr.equus.application.domain.application.model.Application

interface QueryApplicationInfoListByStatusIsSubmittedPort {
    fun queryApplicationInfoListByStatusIsSubmitted(isSubmitted: Boolean): List<Application>
}