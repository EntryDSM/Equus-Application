package hs.kr.equus.application.domain.applicationCase.spi

import hs.kr.equus.application.domain.applicationCase.model.ApplicationCase

interface CommandApplicationCasePort {
    fun save(applicationCase: ApplicationCase): ApplicationCase

    fun delete(applicationCase: ApplicationCase)
}