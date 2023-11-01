package hs.kr.equus.application.domain.application.spi

import hs.kr.equus.application.domain.graduationInfo.spi.GraduationInfoQueryApplicationPort

interface ApplicationPort :
    CommandApplicationPort,
    QueryApplicationPort,
    GraduationInfoQueryApplicationPort
