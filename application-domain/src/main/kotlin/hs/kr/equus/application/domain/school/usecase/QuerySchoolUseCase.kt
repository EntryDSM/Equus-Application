package hs.kr.equus.application.domain.school.usecase

import hs.kr.equus.application.domain.graduationInfo.spi.GraduationInfoQuerySchoolPort
import hs.kr.equus.application.domain.school.usecase.dto.response.QuerySchoolResponse
import hs.kr.equus.application.domain.school.usecase.dto.response.SchoolResponse
import hs.kr.equus.application.global.annotation.UseCase

@UseCase
class QuerySchoolUseCase(
    private val graduationSchoolPort: GraduationInfoQuerySchoolPort
) {
    fun querySchool(name: String): QuerySchoolResponse {
        val school = graduationSchoolPort.querySchoolBySchoolCode(name) ?: throw Exception()
        return QuerySchoolResponse(
            content = listOf(
                SchoolResponse(
                    code = school.code,
                    name = school.name,
                    information = school.name,
                    address = school.address
                )
            )
        )
    }
}