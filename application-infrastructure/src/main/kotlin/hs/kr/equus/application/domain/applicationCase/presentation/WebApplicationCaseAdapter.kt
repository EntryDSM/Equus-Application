package hs.kr.equus.application.domain.applicationCase.presentation

import hs.kr.equus.application.domain.applicationCase.presentation.dto.request.UpdateGraduationCaseWebRequest
import hs.kr.equus.application.domain.applicationCase.presentation.dto.request.UpdateQualificationCaseWebRequest
import hs.kr.equus.application.domain.applicationCase.usecase.GetGraduationCaseUseCase
import hs.kr.equus.application.domain.applicationCase.usecase.GetQualificationCaseUseCase
import hs.kr.equus.application.domain.applicationCase.usecase.UpdateGraduationCaseUseCase
import hs.kr.equus.application.domain.applicationCase.usecase.UpdateQualificationCaseUseCase
import hs.kr.equus.application.domain.applicationCase.usecase.dto.request.UpdateGraduationCaseRequest
import hs.kr.equus.application.domain.applicationCase.usecase.dto.request.UpdateQualificationCaseRequest
import hs.kr.equus.application.domain.applicationCase.usecase.dto.response.GetGraduationCaseResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/score")
class WebApplicationCaseAdapter(
    private val updateGraduationCaseUseCase: UpdateGraduationCaseUseCase,
    private val updateQualificationCaseUseCase: UpdateQualificationCaseUseCase,
    private val getGraduationCaseUseCase: GetGraduationCaseUseCase,
    private val getQualificationCaseUseCase: GetQualificationCaseUseCase,
) {
    @PatchMapping("/graduation")
    fun updateGraduationCase(
        @RequestBody @Valid request: UpdateGraduationCaseWebRequest,
    ) {
        updateGraduationCaseUseCase.execute(
            request.run {
                UpdateGraduationCaseRequest(
                    volunteerTime = volunteerTime,
                    absenceDayCount = absenceDayCount,
                    lectureAbsenceCount = lectureAbsenceCount,
                    latenessCount = latenessCount,
                    earlyLeaveCount = earlyLeaveCount,
                    koreanGrade = koreanGrade,
                    socialGrade = socialGrade,
                    historyGrade = historyGrade,
                    mathGrade = mathGrade,
                    scienceGrade = scienceGrade,
                    englishGrade = englishGrade,
                    techAndHomeGrade = techAndHomeGrade,
                )
            }
        )
    }

    @PatchMapping("/qualification")
    fun updateQualificationCase(
        @RequestBody @Valid request: UpdateQualificationCaseWebRequest,
    ) {
        updateQualificationCaseUseCase.execute(UpdateQualificationCaseRequest(request.averageScore))
    }

    @GetMapping("/graduation")
    fun getGraduationCase() = getGraduationCaseUseCase.execute()

    @GetMapping("/qualification")
    fun getQualificationCase() = getQualificationCaseUseCase.execute()
}
