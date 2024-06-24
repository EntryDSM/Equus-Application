package hs.kr.equus.application.domain.application.spi

import hs.kr.equus.application.domain.applicationCase.spi.ApplicationCaseQueryApplicationPort
import hs.kr.equus.application.domain.graduationInfo.spi.GraduationInfoQueryApplicationPort
import hs.kr.equus.application.domain.score.spi.ScoreQueryApplicationPort

interface ApplicationPort :
    CommandApplicationPort,
    QueryApplicationPort,
    GraduationInfoQueryApplicationPort,
    ScoreQueryApplicationPort,
    ApplicationCaseQueryApplicationPort,
    QueryStaticsCountPort
