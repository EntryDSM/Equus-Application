package hs.kr.equus.application.domain.application.spi

import hs.kr.equus.application.domain.application.model.Application
import hs.kr.equus.application.domain.applicationCase.model.ApplicationCase

interface ApplicationQueryApplicationCasePort {
    fun queryApplicationCaseByApplication(application: Application): ApplicationCase?
}