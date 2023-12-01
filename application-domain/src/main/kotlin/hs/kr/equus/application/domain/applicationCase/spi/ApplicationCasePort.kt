package hs.kr.equus.application.domain.applicationCase.spi

import hs.kr.equus.application.domain.score.spi.ScoreQueryApplicationCasePort

interface ApplicationCasePort :
        CommandApplicationCasePort,
        QueryApplicationCasePort,
        ScoreQueryApplicationCasePort