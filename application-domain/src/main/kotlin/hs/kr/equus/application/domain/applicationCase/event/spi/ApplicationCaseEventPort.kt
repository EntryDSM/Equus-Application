package hs.kr.equus.application.domain.applicationCase.event.spi

import hs.kr.equus.application.domain.applicationCase.model.ApplicationCase
import hs.kr.equus.application.domain.applicationCase.model.GraduationCase
import hs.kr.equus.application.domain.applicationCase.model.QualificationCase

interface ApplicationCaseEventPort {
    fun updateGraduationCase(originGraduationCase: GraduationCase)
    fun updateQualificationCase(qualificationCase: QualificationCase)
    fun updateApplicationCaseRollback(originApplicationCase: ApplicationCase)
}