package hs.kr.equus.application.domain.application.usecase

import hs.kr.equus.application.domain.application.spi.*
import hs.kr.equus.application.domain.application.usecase.dto.vo.ApplicationInfoVO
import hs.kr.equus.application.domain.applicationCase.model.GraduationCase
import hs.kr.equus.application.domain.graduationInfo.model.Graduation
import hs.kr.equus.application.domain.score.spi.QueryScorePort
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
        val applications = queryApplicationInfoListByStatusIsSubmittedPort.queryApplicationInfoListByStatusIsSubmitted(false)
            .map { it ->
                val graduationInfo = queryGraduationInfoPort.queryGraduationInfoByApplication(it)
                val graduation = graduationInfo as? Graduation
                val applicationCase = queryApplicationCasePort.queryApplicationCaseByApplication(it)
                val graduationCase = applicationCase as? GraduationCase
                val application = queryApplicationPort.queryApplicationByReceiptCode(it.receiptCode)
                val school = applicationQuerySchoolPort.querySchoolBySchoolCode(graduation!!.schoolCode.toString())
                ApplicationInfoVO(
                    application!!,
                    graduation,
                    graduationCase,
                    queryScorePort.queryScoreByReceiptCode(application.receiptCode),
                    school
                )
            }
        printAdmissionTicketPort.execute(httpServletResponse, applications)
    }
}