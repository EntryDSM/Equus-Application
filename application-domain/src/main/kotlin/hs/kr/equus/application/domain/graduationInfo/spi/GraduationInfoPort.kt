package hs.kr.equus.application.domain.graduationInfo.spi

import hs.kr.equus.application.domain.application.spi.ApplicationQueryGraduationInfoPort

interface GraduationInfoPort :
    CommandGraduationInfoPort,
    QueryGraduationInfoPort,
    ApplicationQueryGraduationInfoPort