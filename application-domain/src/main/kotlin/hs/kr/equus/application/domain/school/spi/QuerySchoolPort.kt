package hs.kr.equus.application.domain.school.spi

import hs.kr.equus.application.domain.school.model.School

interface QuerySchoolPort {
    fun querySchoolListBySchoolName(schoolName: String): List<School>
}