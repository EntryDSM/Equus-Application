package hs.kr.equus.application.domain.applicationCase.spi

import hs.kr.equus.application.domain.application.model.Application
import hs.kr.equus.application.domain.applicationCase.model.ApplicationCase
import hs.kr.equus.application.domain.applicationCase.model.GraduationCase
import hs.kr.equus.application.domain.applicationCase.model.QualificationCase

interface QueryApplicationCasePort {
    fun queryApplicationCaseByApplication(application: Application): ApplicationCase?
    fun isExistsApplicationCaseByApplication(application: Application): Boolean
}
