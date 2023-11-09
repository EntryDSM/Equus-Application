package hs.kr.equus.application.domain.application.presentation

import hs.kr.equus.application.domain.application.presentation.dto.request.*
import hs.kr.equus.application.domain.application.usecase.*
import hs.kr.equus.application.domain.application.usecase.dto.request.*
import hs.kr.equus.application.domain.application.usecase.dto.response.GetInformationResponse
import hs.kr.equus.application.domain.application.usecase.dto.response.GetIntroduceResponse
import hs.kr.equus.application.domain.application.usecase.dto.response.GetStudyPlanResponse
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/application")
class WebApplicationAdapter(
    private val createApplicationUseCase: CreateApplicationUseCase,
    private val getInformationUseCase: GetInformationUseCase,
    private val getIntroduceUseCase: GetIntroduceUseCase,
    private val getStudyPlanUseCase: GetStudyPlanUseCase,
    private val updateApplicationTypeUseCase: UpdateApplicationTypeUseCase,
    private val updateGraduationTypeUseCase: UpdateGraduationTypeUseCase,
    private val updateInformationUseCase: UpdateInformationUseCase,
    private val updateIntroduceUseCase: UpdateIntroduceUseCase,
    private val updateStudyPlanUseCase: UpdateStudyPlanUseCase,
) {
    @PostMapping
    fun createApplication() {
        createApplicationUseCase.execute()
    }

    @GetMapping
    fun getInformation(): GetInformationResponse {
        return getInformationUseCase.execute()
    }

    @GetMapping("/intro")
    fun getIntroduce(): GetIntroduceResponse {
        return getIntroduceUseCase.execute()
    }

    @GetMapping("/study-plan")
    fun getStudyPlan(): GetStudyPlanResponse {
        return getStudyPlanUseCase.execute()
    }

    @PatchMapping("/type")
    fun updateApplicationType(
        @RequestBody @Valid request: UpdateApplicationTypeWebRequest,
    ) {
        updateApplicationTypeUseCase.execute(
            request.run {
                UpdateApplicationTypeRequest(
                    applicationType = applicationType,
                    applicationRemark = applicationRemark,
                    isDaejeon = isDaejeon,
                    isOutOfHeadcount = isOutOfHeadcount,
                )
            },
        )
    }

    @PatchMapping("/graduation/type")
    fun updateGraduationType(
        @RequestBody @Valid request: UpdateGraduationTypeWebRequest,
    ) {
        updateGraduationTypeUseCase.execute(
            request.run {
                UpdateGraduationTypeRequest(
                    graduateDate = graduateDate,
                    educationalStatus = educationalStatus,
                )
            },
        )
    }

    @PatchMapping
    fun updateInformation(
        @RequestBody @Valid request: UpdateInformationWebRequest,
    ) {
        updateInformationUseCase.execute(
            request.run {
                UpdateInformationRequest(
                    sex = sex,
                    birthDate = birthDate,
                    applicantName = applicantName,
                    applicantTel = applicantTel,
                    parentTel = parentTel,
                    parentName = parentName,
                    streetAddress = streetAddress,
                    postalCode = postalCode,
                    detailAddress = detailAddress,
                )
            },
        )
    }

    @PatchMapping("/intro")
    fun updateIntroduce(
        @RequestBody @Valid request: UpdateIntroduceWebRequest,
    ) {
        updateIntroduceUseCase.execute(
            UpdateIntroduceRequest(request.content),
        )
    }

    @PatchMapping("/study-plan")
    fun updateStudyPlan(
        @RequestBody @Valid request: UpdateStudyPlanWebRequest,
    ) {
        updateStudyPlanUseCase.execute(
            UpdateStudyPlanRequest(request.content),
        )
    }
}
