package hs.kr.equus.application.domain.application.usecase

import hs.kr.equus.application.domain.application.spi.*
import hs.kr.equus.application.domain.application.usecase.dto.vo.ApplicationInfoVO
import hs.kr.equus.application.domain.graduationInfo.model.Graduation
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
        val receiptCodeList = applications.map { it.receiptCode }
        val graduationInfoMap = queryGraduationInfoPort.queryAllGraduationByReceiptCode(receiptCodeList)
            .filterNotNull()
            .associateBy { it.receiptCode }
        val applicationCaseMap = queryApplicationCasePort.queryAllApplicationCaseByReceiptCode(receiptCodeList)
            .filterNotNull()
            .associateBy { it.receiptCode }
        val applicationMap = queryApplicationPort.queryAllByReceiptCode(receiptCodeList)
            .filterNotNull()
            .associateBy { it.receiptCode }
        val schoolMap = graduationInfoMap.values
            .filterIsInstance<Graduation>()
            .associate {
                val schoolCode = it.schoolCode
                val school = applicationQuerySchoolPort.querySchoolBySchoolCode(schoolCode!!)
                schoolCode to school
            }
        val scoreList = queryScorePort.queryAllByReceiptCode(receiptCodeList)
            .filterNotNull()
            .associateBy { it.receiptCode }
        val result = applications.map {
            val application = applicationMap[it.receiptCode]
            val graduationInfo = graduationInfoMap[it.receiptCode]
            val applicationCase = applicationCaseMap[it.receiptCode]
            val school = (graduationInfo as? Graduation)?.schoolCode.let { schoolMap[it] }
            val score = scoreList[it.receiptCode]
            ApplicationInfoVO(
                application!!,
                graduationInfo,
                applicationCase,
                score,
                school
            )
        }
        printAdmissionTicketPort.execute(httpServletResponse, result)
    }
}