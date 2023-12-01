package hs.kr.equus.application.domain.application.presentation

import hs.kr.equus.application.domain.application.presentation.dto.request.UpdateApplicationTypeWebRequest
import hs.kr.equus.application.domain.application.presentation.dto.request.UpdateEducationalStatusWebRequest
import hs.kr.equus.application.domain.application.presentation.dto.request.UpdateInformationWebRequest
import hs.kr.equus.application.domain.application.presentation.dto.request.UpdateIntroduceWebRequest
import hs.kr.equus.application.domain.application.presentation.dto.request.UpdateStudyPlanWebRequest
import hs.kr.equus.application.domain.application.usecase.CreateApplicationUseCase
import hs.kr.equus.application.domain.application.usecase.GetInformationUseCase
import hs.kr.equus.application.domain.application.usecase.GetIntroduceUseCase
import hs.kr.equus.application.domain.application.usecase.GetStudyPlanUseCase
import hs.kr.equus.application.domain.application.usecase.UpdateApplicationTypeUseCase
import hs.kr.equus.application.domain.application.usecase.UpdateEducationalStatusUseCase
import hs.kr.equus.application.domain.application.usecase.UpdateInformationUseCase
import hs.kr.equus.application.domain.application.usecase.UpdateIntroduceUseCase
import hs.kr.equus.application.domain.application.usecase.UpdateStudyPlanUseCase
import hs.kr.equus.application.domain.application.usecase.dto.request.UpdateApplicationTypeRequest
import hs.kr.equus.application.domain.application.usecase.dto.request.UpdateEducationalStatusRequest
import hs.kr.equus.application.domain.application.usecase.dto.request.UpdateInformationRequest
import hs.kr.equus.application.domain.application.usecase.dto.request.UpdateIntroduceRequest
import hs.kr.equus.application.domain.application.usecase.dto.request.UpdateStudyPlanRequest

import hs.kr.equus.application.domain.application.usecase.dto.response.GetInformationResponse
import hs.kr.equus.application.domain.application.usecase.dto.response.GetIntroduceResponse
import hs.kr.equus.application.domain.application.usecase.dto.response.GetStudyPlanResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import javax.validation.Valid

@RestController
@RequestMapping("/application")
class WebApplicationAdapter(
    private val createApplicationUseCase: CreateApplicationUseCase,
    private val getInformationUseCase: GetInformationUseCase,
    private val getIntroduceUseCase: GetIntroduceUseCase,
    private val getStudyPlanUseCase: GetStudyPlanUseCase,
    private val updateApplicationTypeUseCase: UpdateApplicationTypeUseCase,
    private val updateEducationalStatusUseCase: UpdateEducationalStatusUseCase,
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
    fun updateEducationalStatus(
        @RequestBody @Valid request: UpdateEducationalStatusWebRequest,
    ) {
        updateEducationalStatusUseCase.execute(
            request.run {
                UpdateEducationalStatusRequest(
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
