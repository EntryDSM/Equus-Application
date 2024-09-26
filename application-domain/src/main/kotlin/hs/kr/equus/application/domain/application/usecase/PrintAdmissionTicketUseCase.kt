package hs.kr.equus.application.domain.application.usecase

import hs.kr.equus.application.domain.application.spi.*
import hs.kr.equus.application.domain.application.usecase.dto.vo.ApplicationInfoVO
import hs.kr.equus.application.domain.applicationCase.exception.ApplicationCaseExceptions
import hs.kr.equus.application.domain.applicationCase.model.GraduationCase
import hs.kr.equus.application.domain.graduationInfo.exception.GraduationInfoExceptions
import hs.kr.equus.application.domain.graduationInfo.model.Graduation
import hs.kr.equus.application.domain.graduationInfo.model.Qualification
import hs.kr.equus.application.global.annotation.ReadOnlyUseCase
import javax.servlet.http.HttpServletResponse

@ReadOnlyUseCase
class PrintAdmissionTicketUseCase(
    private val printAdmissionTicketPort: PrintAdmissionTicketPort,
    private val queryGraduationInfoPort: ApplicationQueryGraduationInfoPort,
    private val queryApplicationCasePort: ApplicationQueryApplicationCasePort,
    private val queryApplicationPort: QueryApplicationPort,
    private val applicationQuerySchoolPort: ApplicationQuerySchoolPort,
    private val queryScorePort: ApplicationQueryScorePort,
    private val queryApplicationInfoListByStatusIsSubmittedPort: QueryApplicationInfoListByStatusIsSubmittedPort
){
    fun execute(httpServletResponse: HttpServletResponse) {
        val applications = queryApplicationInfoListByStatusIsSubmittedPort.queryApplicationInfoListByStatusIsSubmitted(true)
            .map { it ->
                val graduationInfo = queryGraduationInfoPort.queryGraduationInfoByApplication(it)
                    ?: throw GraduationInfoExceptions.GraduationNotFoundException()
                val result = when (graduationInfo) {
                    is Graduation -> {
                        val applicationCase = queryApplicationCasePort.queryApplicationCaseByApplication(it)
                            ?: throw ApplicationCaseExceptions.ApplicationCaseNotFoundException()
                        val graduationCase = applicationCase as GraduationCase
                        val application = queryApplicationPort.queryApplicationByReceiptCode(it.receiptCode)
                        val school =
                            applicationQuerySchoolPort.querySchoolBySchoolCode(graduationInfo.schoolCode.toString())
                        ApplicationInfoVO(
                            application!!,
                            graduationInfo,
                            graduationCase,
                            queryScorePort.queryScoreByReceiptCode(application.receiptCode),
                            school
                        )
                    }

                    is Qualification -> {
                        val applicationCase = queryApplicationCasePort.queryApplicationCaseByApplication(it)
                            ?: throw ApplicationCaseExceptions.ApplicationCaseNotFoundException()
                        val graduationCase = applicationCase as GraduationCase
                        val application = queryApplicationPort.queryApplicationByReceiptCode(it.receiptCode)
                        ApplicationInfoVO(
                            application!!,
                            graduationInfo,
                            graduationCase,
                            queryScorePort.queryScoreByReceiptCode(application.receiptCode),
                            null
                        )
                    }
                }
                result
            }

        printAdmissionTicketPort.execute(httpServletResponse, applications)
    }
}