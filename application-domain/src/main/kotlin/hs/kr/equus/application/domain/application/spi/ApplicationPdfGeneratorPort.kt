package hs.kr.equus.application.domain.application.spi

import hs.kr.equus.application.domain.application.model.Application
import hs.kr.equus.application.domain.score.model.Score

interface ApplicationPdfGeneratorPort {
    fun generate(application: Application, score: Score): ByteArray
}