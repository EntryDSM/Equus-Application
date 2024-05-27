package hs.kr.equus.application.domain.school.presentation

import hs.kr.equus.application.domain.school.usecase.QuerySchoolUseCase
import hs.kr.equus.application.domain.school.presentation.dto.response.QuerySchoolWebResponse
import hs.kr.equus.application.domain.school.presentation.dto.response.SchoolWebResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/schools")
class WebSchoolAdapter(
    private val querySchoolUseCase: QuerySchoolUseCase
) {
    @GetMapping
    fun querySchool (
        @RequestParam(value = "school_name")
        name: String
    ): QuerySchoolWebResponse {
        return QuerySchoolWebResponse(
            content = querySchoolUseCase.querySchool(name).content.map {
                SchoolWebResponse(
                    code = it.code,
                    name = it.name,
                    information = it.information,
                    address = it.address
                )
            }
        )
    }
}