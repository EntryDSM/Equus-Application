package hs.kr.equus.application.domain.graduationInfo.spi

import hs.kr.equus.application.domain.school.model.School

interface GraduationQuerySchoolPort {
    fun querySchoolBySchoolCode(schoolCode: String): School?

    fun isExistsSchoolBySchoolCode(schoolCode: String): Boolean
}
