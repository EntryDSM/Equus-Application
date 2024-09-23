package hs.kr.equus.application.domain.applicationCase.event.spi

import hs.kr.equus.application.domain.applicationCase.model.ApplicationCase

interface ApplicationCaseEventPort {
    fun updateApplicationCase(originApplicationCase: ApplicationCase)
    fun updateApplicationCaseRollback(originApplicationCase: ApplicationCase)
}