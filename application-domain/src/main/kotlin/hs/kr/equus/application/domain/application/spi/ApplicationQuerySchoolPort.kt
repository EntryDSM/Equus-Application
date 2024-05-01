package hs.kr.equus.application.domain.application.spi

import hs.kr.equus.application.domain.school.model.School

interface ApplicationQuerySchoolPort {
    fun querySchoolBySchoolCode(schoolCode: String): School?
}