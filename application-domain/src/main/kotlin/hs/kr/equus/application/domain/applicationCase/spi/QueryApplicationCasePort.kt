package hs.kr.equus.application.domain.applicationCase.spi

import hs.kr.equus.application.domain.application.model.Application
import hs.kr.equus.application.domain.applicationCase.model.ApplicationCase

interface QueryApplicationCasePort {
    fun queryApplicationCaseByApplication(application: Application): ApplicationCase?
    fun isExistsApplicationCaseByApplication(application: Application): Boolean
}