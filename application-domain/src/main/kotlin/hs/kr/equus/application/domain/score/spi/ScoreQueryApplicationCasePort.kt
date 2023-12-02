package hs.kr.equus.application.domain.score.spi

import hs.kr.equus.application.domain.application.model.Application
import hs.kr.equus.application.domain.applicationCase.model.ApplicationCase

interface ScoreQueryApplicationCasePort {
    fun queryApplicationCaseByApplication(application: Application): ApplicationCase?
}