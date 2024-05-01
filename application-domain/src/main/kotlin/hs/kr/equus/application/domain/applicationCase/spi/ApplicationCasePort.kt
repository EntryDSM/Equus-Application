package hs.kr.equus.application.domain.applicationCase.spi

import hs.kr.equus.application.domain.application.spi.ApplicationQueryApplicationCasePort
import hs.kr.equus.application.domain.score.spi.ScoreQueryApplicationCasePort

interface ApplicationCasePort :
        CommandApplicationCasePort,
        QueryApplicationCasePort,
        ScoreQueryApplicationCasePort,
        ApplicationQueryApplicationCasePort
