package hs.kr.equus.application.domain.score.spi

import hs.kr.equus.application.domain.application.spi.ApplicationQueryScorePort

interface ScorePort :
        CommandScorePort,
        QueryScorePort,
        ApplicationQueryScorePort,
        QueryTotalScorePort
