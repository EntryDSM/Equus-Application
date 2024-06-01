package hs.kr.equus.application.global.pdf

import hs.kr.equus.application.domain.application.exception.ApplicationExceptions
import hs.kr.equus.application.domain.application.model.Application
import hs.kr.equus.application.domain.application.spi.QueryApplicationPort
import hs.kr.equus.application.domain.graduationInfo.exception.GraduationInfoExceptions
import hs.kr.equus.application.domain.score.exception.ScoreExceptions.ScoreNotFoundException
import hs.kr.equus.application.domain.score.spi.ExistsScorePort
import hs.kr.equus.application.domain.score.spi.QueryScorePort
import hs.kr.equus.application.global.security.spi.SecurityPort
import org.springframework.stereotype.Component


@Component
class ApplicationPdfUseCase(
    private val securityPort: SecurityPort,
    private val queryApplicationPort: QueryApplicationPort,
    private val existsScorePort: ExistsScorePort,
    private val queryScorePort: QueryScorePort,
    private val applicationPdfGenerator: ApplicationPdfGenerator
) {

    fun getPreviewApplicationPdf(): ByteArray {
        val userId = securityPort.getCurrentUserId()
        val application = queryApplicationPort.queryApplicationByUserId(userId)
            ?: throw ApplicationExceptions.ApplicationNotFoundException()

        validatePrintableApplicant(application)

        val calculatedScore = queryScorePort.queryScoreByReceiptCode(application.receiptCode)

        return applicationPdfGenerator.generate(application, calculatedScore!!)
    }

    private fun validatePrintableApplicant(application: Application) {
        if (application.isEducationalStatusEmpty()) throw GraduationInfoExceptions.EducationalStatusUnmatchedException()
    }
}