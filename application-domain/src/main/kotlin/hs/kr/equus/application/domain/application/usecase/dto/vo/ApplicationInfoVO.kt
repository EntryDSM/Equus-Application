package hs.kr.equus.application.domain.application.usecase.dto.vo

import hs.kr.equus.application.domain.application.model.Application
import hs.kr.equus.application.domain.applicationCase.model.ApplicationCase
import hs.kr.equus.application.domain.graduationInfo.model.GraduationInfo
import hs.kr.equus.application.domain.school.model.School
import hs.kr.equus.application.domain.score.model.Score

data class ApplicationInfoVO (
    val application: Application,
    val graduationInfo: GraduationInfo? = null,
    val applicationCase: ApplicationCase? = null,
    val score: Score? = null,
    val school: School? = null
)
