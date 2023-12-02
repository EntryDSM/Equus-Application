package hs.kr.equus.application.domain.score.spi

import hs.kr.equus.application.domain.score.model.Score

interface CommandScorePort {
    fun save(score: Score): Score
}