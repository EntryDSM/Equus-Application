package hs.kr.equus.application.domain.school.spi

import hs.kr.equus.application.domain.application.spi.ApplicationQuerySchoolPort
import hs.kr.equus.application.domain.graduationInfo.spi.GraduationInfoQuerySchoolPort

interface SchoolPort :
    GraduationInfoQuerySchoolPort,
    QuerySchoolPort,
    ApplicationQuerySchoolPort
