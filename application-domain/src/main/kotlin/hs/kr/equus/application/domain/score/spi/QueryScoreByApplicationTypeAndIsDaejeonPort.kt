package hs.kr.equus.application.domain.score.spi

import hs.kr.equus.application.domain.application.model.types.ApplicationType
import hs.kr.equus.application.domain.score.model.Score

interface QueryScoreByApplicationTypeAndIsDaejeonPort {
    fun queryScoreByApplicationTypeAndIsDaejeon(applicationType: ApplicationType, isDaejeon: Boolean): List<Score?>
}
