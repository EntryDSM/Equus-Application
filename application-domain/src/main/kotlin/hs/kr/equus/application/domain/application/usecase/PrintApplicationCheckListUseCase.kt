package hs.kr.equus.application.domain.application.usecase

import hs.kr.equus.application.domain.application.spi.*
import hs.kr.equus.application.domain.application.usecase.dto.vo.ApplicationInfoVO
import hs.kr.equus.application.domain.applicationCase.model.GraduationCase
import hs.kr.equus.application.domain.graduationInfo.model.Graduation
import hs.kr.equus.application.global.annotation.ReadOnlyUseCase
import javax.servlet.http.HttpServletResponse

@ReadOnlyUseCase
class PrintApplicationCheckListUseCase(
    private val printApplicationCheckListPort: PrintApplicationCheckListPort,
    private val queryApplicationInfoListByStatusIsSubmittedPort: QueryApplicationInfoListByStatusIsSubmittedPort,
    private val queryApplicationPort: QueryApplicationPort,
    private val queryScorePort: ApplicationQueryScorePort,
    private val queryApplicationCasePort: ApplicationQueryApplicationCasePort,
    private val queryGraduationInfoPort: ApplicationQueryGraduationInfoPort,
    private val applicationQuerySchoolPort: ApplicationQuerySchoolPort
) {
    fun execute(httpServletResponse: HttpServletResponse) {
        val applicationList = queryApplicationInfoListByStatusIsSubmittedPort.queryApplicationInfoListByStatusIsSubmitted(true)

        val schoolCodeList = applicationList.mapNotNull {
            val graduationInfo = queryGraduationInfoPort.queryGraduationInfoByApplication(it) as? Graduation
            graduationInfo?.schoolCode
        }.distinct()

        val schoolMap = schoolCodeList.associateWith {
            applicationQuerySchoolPort.querySchoolBySchoolCode(it)
        }

        val applicationInfoVOList = applicationList.map { it ->
            val graduationInfo = queryGraduationInfoPort.queryGraduationInfoByApplication(it) as? Graduation
            val applicationCase = queryApplicationCasePort.queryApplicationCaseByApplication(it) as? GraduationCase
            val application = queryApplicationPort.queryApplicationByReceiptCode(it.receiptCode)
            val school = graduationInfo?.schoolCode?.let { schoolMap[it] }

            ApplicationInfoVO(
                application!!,
                graduationInfo,
                applicationCase,
                queryScorePort.queryScoreByReceiptCode(application.receiptCode),
                school
            )
        }

        printApplicationCheckListPort.printApplicationCheckList(applicationInfoVOList, httpServletResponse)
    }
}
