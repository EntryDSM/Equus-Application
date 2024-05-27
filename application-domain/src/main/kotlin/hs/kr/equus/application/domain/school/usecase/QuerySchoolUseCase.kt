package hs.kr.equus.application.domain.school.usecase

import hs.kr.equus.application.domain.school.spi.QuerySchoolPort
import hs.kr.equus.application.domain.school.usecase.dto.response.QuerySchoolResponse
import hs.kr.equus.application.domain.school.usecase.dto.response.SchoolResponse
import hs.kr.equus.application.global.annotation.ReadOnlyUseCase

@ReadOnlyUseCase
class QuerySchoolUseCase(
    private val querySchoolPort: QuerySchoolPort
) {
    fun querySchool(name: String): QuerySchoolResponse {
        val schoolList = querySchoolPort.querySchoolListBySchoolName(name)
        return QuerySchoolResponse(
            content = schoolList.map {
                SchoolResponse(
                    code = it.code,
                    name = it.name,
                    information = it.tel,
                    address = it.address
                )
            }
        )
    }
}