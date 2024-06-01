package hs.kr.equus.application.domain.score.spi

interface ScorePort :
        CommandScorePort,
        QueryScorePort,
        ExistsScorePort