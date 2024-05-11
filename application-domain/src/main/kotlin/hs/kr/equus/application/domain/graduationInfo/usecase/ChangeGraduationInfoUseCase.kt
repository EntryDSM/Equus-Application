package hs.kr.equus.application.domain.graduationInfo.usecase

import hs.kr.equus.application.domain.application.exception.ApplicationExceptions
import hs.kr.equus.application.domain.graduationInfo.factory.GraduationInfoFactory

import hs.kr.equus.application.domain.graduationInfo.spi.CommandGraduationInfoPort
import hs.kr.equus.application.domain.graduationInfo.spi.GraduationInfoQueryApplicationPort
import hs.kr.equus.application.domain.graduationInfo.spi.QueryGraduationInfoPort
import hs.kr.equus.application.global.annotation.UseCase

@UseCase
class ChangeGraduationInfoUseCase(
    private val queryGraduationInfoPort: QueryGraduationInfoPort,
    private val graduationInfoQueryApplicationPort: GraduationInfoQueryApplicationPort,
    private val commandGraduationInfoPort: CommandGraduationInfoPort,
    private val graduationInfoFactory: GraduationInfoFactory
) {
    fun execute(receiptCode: Long) {
        val application = graduationInfoQueryApplicationPort.queryApplicationByReceiptCode(receiptCode)
            ?: throw ApplicationExceptions.ApplicationNotFoundException()


        queryGraduationInfoPort.queryGraduationInfoByApplication(application)?.let {
            // todo mapper.toDomain하면 id값이 0으로 변경되는걸 고쳐야함
            commandGraduationInfoPort.delete(it)
        }

        val graduationInfo = graduationInfoFactory.createGraduationInfo(
            receiptCode,
            application.educationalStatus
        )
        commandGraduationInfoPort.save(graduationInfo)
    }
}
