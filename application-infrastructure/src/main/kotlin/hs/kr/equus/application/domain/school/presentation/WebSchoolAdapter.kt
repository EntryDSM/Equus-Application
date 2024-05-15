package hs.kr.equus.application.domain.school.presentation

import hs.kr.equus.application.domain.school.QuerySchoolUseCase
import hs.kr.equus.application.domain.school.usecase.dto.response.QuerySchoolResponse
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
    ): String =
        querySchoolUseCase.querySchool(name)
}