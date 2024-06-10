package hs.kr.equus.application.domain.application.usecase

import hs.kr.equus.application.domain.application.exception.ApplicationExceptions
import hs.kr.equus.application.domain.application.model.Application
import hs.kr.equus.application.domain.application.spi.ApplicationPdfGeneratorPort
import hs.kr.equus.application.domain.application.spi.ApplicationQueryStatusPort
import hs.kr.equus.application.domain.application.spi.QueryApplicationPort
import hs.kr.equus.application.domain.graduationInfo.exception.GraduationInfoExceptions
import hs.kr.equus.application.domain.score.exception.ScoreExceptions
import hs.kr.equus.application.domain.score.model.Score
import hs.kr.equus.application.domain.score.spi.QueryScorePort
import hs.kr.equus.application.domain.status.exception.StatusExceptions
import hs.kr.equus.application.global.annotation.ReadOnlyUseCase
import hs.kr.equus.application.global.security.spi.SecurityPort
import java.util.UUID


@ReadOnlyUseCase
class GetPreviewApplicationPdfUseCase(
    private val securityPort: SecurityPort,
    private val queryApplicationPort: QueryApplicationPort,
    private val queryScorePort: QueryScorePort,
    private val applicationPdfGeneratorPort: ApplicationPdfGeneratorPort,
    private val queryStatusPort: ApplicationQueryStatusPort
) {

    fun execute(): ByteArray {
        val userId = securityPort.getCurrentUserId()
        val application =  queryApplicationPort.queryApplicationByUserId(userId)
            ?: throw ApplicationExceptions.ApplicationNotFoundException()

        validatePrintableApplicant(application)

        val calculatedScore = queryScorePort.queryScoreByReceiptCode(application.receiptCode)
            ?: throw ScoreExceptions.ScoreNotFoundException()

        return applicationPdfGeneratorPort.generate(application, calculatedScore)
    }

    private fun validatePrintableApplicant(application: Application) {
        if (application.isEducationalStatusEmpty())
            throw GraduationInfoExceptions.EducationalStatusUnmatchedException()
    }
}