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
        val applicationInfoVOList =
            queryApplicationInfoListByStatusIsSubmittedPort.queryApplicationInfoListByStatusIsSubmitted(true)
                .map { it ->
                    val graduationInfo = queryGraduationInfoPort.queryGraduationInfoByApplication(it)
                    val graduation = graduationInfo as? Graduation
                    val applicationCase = queryApplicationCasePort.queryApplicationCaseByApplication(it)
                    val graduationCase = applicationCase as? GraduationCase
                    val application = queryApplicationPort.queryApplicationByReceiptCode(it.receiptCode)
                    val school = graduation?.schoolCode?.let { schoolCode ->
                        applicationQuerySchoolPort.querySchoolBySchoolCode(schoolCode)
                    }
                    ApplicationInfoVO(
                        application!!,
                        graduation,
                        graduationCase,
                        queryScorePort.queryScoreByReceiptCode(application.receiptCode),
                        school
                    )
                }

        printApplicationCheckListPort.printApplicationCheckList(applicationInfoVOList, httpServletResponse)

    }
}
