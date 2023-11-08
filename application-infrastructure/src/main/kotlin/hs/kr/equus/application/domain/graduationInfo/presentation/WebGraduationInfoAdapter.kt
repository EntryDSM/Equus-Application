package hs.kr.equus.application.domain.graduationInfo.presentation

import hs.kr.equus.application.domain.graduationInfo.presentation.dto.request.UpdateGraduationInformationWebRequest
import hs.kr.equus.application.domain.graduationInfo.usecase.GetGraduationInformationUseCase
import hs.kr.equus.application.domain.graduationInfo.usecase.UpdateGraduationInformationUseCase
import hs.kr.equus.application.domain.graduationInfo.usecase.dto.request.UpdateGraduationInformationRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/application")
class WebGraduationInfoAdapter(
    private val getGraduationInformationUseCase: GetGraduationInformationUseCase,
    private val updateGraduationInformationUseCase: UpdateGraduationInformationUseCase,
) {
    @GetMapping("/graduation")
    fun getGraduationInformation() = getGraduationInformationUseCase.execute()

    @PatchMapping("/graduation")
    fun updateGraduationInformation(
        @RequestBody @Valid request: UpdateGraduationInformationWebRequest,
    ) {
        updateGraduationInformationUseCase.execute(
            request.run {
                UpdateGraduationInformationRequest(
                    gradeNumber = gradeNumber,
                    classNumber = classNumber,
                    studentNumber = studentNumber,
                    schoolCode = schoolCode,
                )
            },
        )
    }
}
