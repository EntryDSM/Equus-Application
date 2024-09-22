package hs.kr.equus.application.domain.application.usecase

import hs.kr.equus.application.domain.application.spi.*
import hs.kr.equus.application.domain.application.usecase.dto.vo.ApplicationInfoVO
import hs.kr.equus.application.domain.applicationCase.model.GraduationCase
import hs.kr.equus.application.domain.graduationInfo.model.Graduation
import hs.kr.equus.application.global.annotation.ReadOnlyUseCase
import javax.servlet.http.HttpServletResponse

@ReadOnlyUseCase
class PrintApplicationInfoUseCase(
    private val printApplicationInfoPort: PrintApplicationInfoPort,
    private val queryApplicationInfoListByStatusIsSubmittedPort: QueryApplicationInfoListByStatusIsSubmittedPort,
    private val queryApplicationPort: QueryApplicationPort,
    private val queryScorePort: ApplicationQueryScorePort,
    private val queryApplicationCasePort: ApplicationQueryApplicationCasePort,
    private val queryGraduationInfoPort: ApplicationQueryGraduationInfoPort,
) {
    fun execute(httpServletResponse: HttpServletResponse) {
        val applications = queryApplicationInfoListByStatusIsSubmittedPort
            .queryApplicationInfoListByStatusIsSubmitted(true)
            .map { application ->
                val graduationInfo = queryGraduationInfoPort.queryGraduationInfoByApplication(application)
                val graduation = graduationInfo as? Graduation
                val applicationCase = queryApplicationCasePort.queryApplicationCaseByApplication(application)
                val graduationCase = applicationCase as? GraduationCase
                ApplicationInfoVO(
                    queryApplicationPort.queryApplicationByReceiptCode(application.receiptCode)!!,
                    graduation,
                    graduationCase,
                    queryScorePort.queryScoreByReceiptCode(application.receiptCode),
                )
            }
        printApplicationInfoPort.execute(httpServletResponse, applications)
    }

}