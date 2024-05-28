package hs.kr.equus.application.domain.application.usecase

import hs.kr.equus.application.domain.application.event.spi.ApplicationEventPort
import hs.kr.equus.application.domain.application.exception.ApplicationExceptions
import hs.kr.equus.application.domain.application.spi.*
import hs.kr.equus.application.domain.graduationInfo.exception.GraduationInfoExceptions
import hs.kr.equus.application.domain.score.exception.ScoreExceptions
import hs.kr.equus.application.domain.status.exception.StatusExceptions
import hs.kr.equus.application.global.annotation.UseCase
import hs.kr.equus.application.global.security.spi.SecurityPort

@UseCase
class SubmitApplicationFinalUseCase(
    private val securityPort: SecurityPort,
    private val queryApplicationPort: QueryApplicationPort,
    private val applicationQueryUserPort: ApplicationQueryUserPort,
    private val applicationQueryGraduationInfoPort: ApplicationQueryGraduationInfoPort,
    private val applicationQueryStatusPort: ApplicationQueryStatusPort,
    private val applicationQueryScorePort: ApplicationQueryScorePort,
    private val applicationEventPort: ApplicationEventPort
) {
   fun execute(){
       val userId = securityPort.getCurrentUserId()

       val application = queryApplicationPort.queryApplicationByUserId(userId)
           ?: throw ApplicationExceptions.ApplicationNotFoundException()
       val graduationInfo = applicationQueryGraduationInfoPort.queryGraduationInfoByApplication(application)
           ?: throw GraduationInfoExceptions.GraduationNotFoundException()

       val score = applicationQueryScorePort.queryScoreByReceiptCode(application.receiptCode)

       if(graduationInfo.hasEmptyInfo() || score == null) {
            throw ApplicationExceptions.ApplicationProcessNotComplete()
       }

       val status = applicationQueryStatusPort.queryStatusByReceiptCode(application.receiptCode)
           ?: throw StatusExceptions.StatusNotFoundException()

       if(status.submittedAt != null) {
            throw StatusExceptions.AlreadySubmittedException()
       }

       applicationEventPort.submitApplicationFinal(application.receiptCode)
   }
}