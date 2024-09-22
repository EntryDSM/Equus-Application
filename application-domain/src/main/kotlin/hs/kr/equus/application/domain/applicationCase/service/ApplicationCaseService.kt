package hs.kr.equus.application.domain.applicationCase.service

import hs.kr.equus.application.domain.application.model.Application
import hs.kr.equus.application.domain.application.model.types.EducationalStatus
import hs.kr.equus.application.domain.applicationCase.exception.ApplicationCaseExceptions
import hs.kr.equus.application.domain.applicationCase.model.ApplicationCase
import hs.kr.equus.application.domain.applicationCase.model.GraduationCase
import hs.kr.equus.application.domain.applicationCase.model.QualificationCase
import hs.kr.equus.application.global.annotation.DomainService

@DomainService
class ApplicationCaseService {
    fun hasEducationalStatusMismatch(application: Application, applicationCase: ApplicationCase): Boolean {
        application.educationalStatus ?: throw ApplicationCaseExceptions.EducationalStatusUnmatchedException()

        return when (application.educationalStatus) {
            EducationalStatus.GRADUATE, EducationalStatus.PROSPECTIVE_GRADUATE ->
                applicationCase !is GraduationCase
            EducationalStatus.QUALIFICATION_EXAM ->
                applicationCase !is QualificationCase
        }
    }
}