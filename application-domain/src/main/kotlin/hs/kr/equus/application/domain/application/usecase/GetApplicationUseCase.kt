package hs.kr.equus.application.domain.application.usecase

import hs.kr.equus.application.domain.application.exception.ApplicationExceptions
import hs.kr.equus.application.domain.application.model.Application
import hs.kr.equus.application.domain.application.spi.*
import hs.kr.equus.application.domain.application.usecase.dto.response.*
import hs.kr.equus.application.domain.applicationCase.model.GraduationCase
import hs.kr.equus.application.domain.applicationCase.model.QualificationCase
import hs.kr.equus.application.domain.file.spi.GenerateFileUrlPort
import hs.kr.equus.application.domain.file.usecase.`object`.PathList
import hs.kr.equus.application.domain.graduationInfo.exception.GraduationInfoExceptions
import hs.kr.equus.application.domain.graduationInfo.model.Graduation
import hs.kr.equus.application.domain.school.exception.SchoolExceptions
import hs.kr.equus.application.domain.status.exception.StatusExceptions
import hs.kr.equus.application.domain.status.model.Status
import hs.kr.equus.application.global.annotation.UseCase

@UseCase
class GetApplicationUseCase(
    private val queryApplicationPort: QueryApplicationPort,
    private val applicationQueryUserPort: ApplicationQueryUserPort,
    private val applicationQuerySchoolPort: ApplicationQuerySchoolPort,
    private val applicationQueryGraduationInfoPort: ApplicationQueryGraduationInfoPort,
    private val applicationQueryApplicationCasePort: ApplicationQueryApplicationCasePort,
    private val applicationQueryStatusPort: ApplicationQueryStatusPort,
    private val generateFileUrlPort: GenerateFileUrlPort
) {
    fun execute(receiptCode: Long): GetApplicationResponse {
        val application = queryApplicationPort.queryApplicationByReceiptCode(receiptCode)
            ?: throw ApplicationExceptions.ApplicationNotFoundException()
        val status = applicationQueryStatusPort.queryStatusByReceiptCode(receiptCode)
            ?: throw StatusExceptions.StatusNotFoundException()

        val statusResponse = getStatusResponse(status)
        val commonInformationResponse = getCommonInformationResponse(application)
        val moreInformationResponse = getMoreInformationResponse(application, status)
        val evaluationResponse = getEvaluationResponse(application, status)

        return GetApplicationResponse(
            status = statusResponse,
            commonInformation = commonInformationResponse,
            moreInformation = moreInformationResponse,
            evaluation = evaluationResponse
        )
    }

    private fun getStatusResponse(status: Status): ApplicationStatusResponse {
        val statusResponse = status.run {
            ApplicationStatusResponse(
                isPrintedArrived = isPrintsArrived,
                isSubmit = isSubmitted
            )
        }
        return statusResponse
    }

    private fun getCommonInformationResponse(application: Application): ApplicationCommonInformationResponse {
        val graduationInfo = applicationQueryGraduationInfoPort.queryGraduationInfoByApplication(application)
            ?: throw GraduationInfoExceptions.GraduationNotFoundException()

        val school = (graduationInfo as? Graduation)?.let {
            applicationQuerySchoolPort.querySchoolBySchoolCode(it.schoolCode!!)
                ?: throw SchoolExceptions.SchoolNotFoundException()
        }

        val user = applicationQueryUserPort.queryUserByUserId(application.userId)

        return ApplicationCommonInformationResponse(
            name = user.name,
            schoolName = school?.name,
            telephoneNumber = user.phoneNumber,
            parentTel = application.parentTel,
            schoolTel = school?.tel
        )
    }

    private fun getMoreInformationResponse(
        application: Application,
        status: Status
    ): ApplicationMoreInformationResponse? {
        if(!status.isSubmitted) {
            return null
        }
        return ApplicationMoreInformationResponse(
            photoUrl = generateFileUrlPort.generateFileUrl(application.photoPath!!, PathList.PHOTO),
            birthDay = application.birthDate!!,
            educationalStatus = application.educationalStatus!!,
            applicationRemark = application.applicationRemark,
            applicationType = application.applicationType!!,
            address = application.streetAddress!!,
            detailAddress = application.detailAddress!!,
            headCount = null
        )
    }

    private fun getEvaluationResponse(application: Application, status: Status): ApplicationEvaluationResponse? {
        if(!status.isSubmitted) {
            return null
        }

        val applicationCase = applicationQueryApplicationCasePort.queryApplicationCaseByApplication(application)
            ?: throw ApplicationExceptions.ApplicationNotFoundException()

        var applicationEvaluationResponse: ApplicationEvaluationResponse? = null
        if(applicationCase is GraduationCase) {
            applicationCase.run {
                applicationEvaluationResponse = ApplicationEvaluationResponse(
                    volunteerTime = volunteerTime,
                    conversionScore = applicationCase.calculateTotalGradeScore(application.isCommon()),
                    dayAbsenceCount = absenceDayCount,
                    lectureAbsenceCount = lectureAbsenceCount,
                    earlyLeaveCount = earlyLeaveCount,
                    latenessCount = latenessCount,
                    averageScore = null,
                    selfIntroduce = application.selfIntroduce!!,
                    studyPlan = application.studyPlan!!
                )
            }
        }

        if(applicationCase is QualificationCase) {
            applicationCase.run {
                applicationEvaluationResponse = ApplicationEvaluationResponse(
                    volunteerTime = null,
                    conversionScore = applicationCase.calculateTotalGradeScore(application.isCommon()),
                    dayAbsenceCount = null,
                    lectureAbsenceCount = null,
                    earlyLeaveCount = null,
                    latenessCount = null,
                    averageScore = averageScore,
                    selfIntroduce = application.selfIntroduce!!,
                    studyPlan = application.studyPlan!!
                )
            }
        }
        return applicationEvaluationResponse
    }
}