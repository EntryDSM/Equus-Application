package hs.kr.equus.application.domain.application.presentation

import hs.kr.equus.application.domain.application.presentation.dto.request.*
import hs.kr.equus.application.domain.application.usecase.*
import hs.kr.equus.application.domain.application.usecase.dto.request.*
import hs.kr.equus.application.domain.application.usecase.dto.response.*
import hs.kr.equus.application.domain.file.presentation.converter.ImageFileConverter
import hs.kr.equus.application.domain.file.presentation.dto.response.UploadImageWebResponse
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
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
    private val uploadPhotoUseCase: UploadPhotoUseCase,
    private val getApplicationTypeUseCase: GetApplicationTypeUseCase,
    private val submitApplicationFinalUseCase: SubmitApplicationFinalUseCase,
    private val getMyApplicationStatusUseCase: GetMyApplicationStatusUseCase
) {
    @PostMapping
    fun createApplication() {
        createApplicationUseCase.execute()
    }

    @PostMapping("/photo")
    fun uploadFile(@RequestPart(name = "image") file: MultipartFile): UploadImageWebResponse {
        return UploadImageWebResponse(
            uploadPhotoUseCase.execute(
                file.let(ImageFileConverter::transferTo)
            )
        )
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
                    veteransNumber = veteransNumber
                )
            },
        )
    }

    @GetMapping("/type")
    fun getApplicationType(): GetApplicationTypeResponse = getApplicationTypeUseCase.execute()

    @PatchMapping("/graduation/type")
    fun updateEducationalStatus(
        @RequestBody @Valid request: UpdateEducationalStatusWebRequest,
    ) {
        updateEducationalStatusUseCase.execute(
            request.run {
                UpdateEducationalStatusRequest(
                    educationalStatus = educationalStatus,
                    graduateDate = graduateDate
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
                    parentRelation = parentRelation
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

//    @PostMapping("/final-submit")
//    fun submitApplicationFinal() = submitApplicationFinalUseCase.execute()

    @GetMapping("/status")
    fun getMyApplicationStatus(): GetApplicationStatusResponse = getMyApplicationStatusUseCase.execute()
}
