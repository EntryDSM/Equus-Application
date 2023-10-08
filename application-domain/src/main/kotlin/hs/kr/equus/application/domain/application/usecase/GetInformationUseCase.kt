package hs.kr.equus.application.domain.application.usecase

import hs.kr.equus.application.domain.application.spi.ApplicationPhotoPort
import hs.kr.equus.application.domain.application.spi.ApplicationSecurityPort
import hs.kr.equus.application.domain.application.spi.QueryApplicationPort
import hs.kr.equus.application.domain.application.usecase.dto.response.GetInformationResponse
import hs.kr.equus.application.global.annotation.ReadOnlyUseCase

@ReadOnlyUseCase
class GetInformationUseCase(
    private val applicationSecurityPort: ApplicationSecurityPort,
    private val queryApplicationPort: QueryApplicationPort,
    private val applicationPhotoPort: ApplicationPhotoPort,
) {
    fun execute(): GetInformationResponse {
        val userId = applicationSecurityPort.getCurrentUserId()
        val application = queryApplicationPort.queryApplicationByUserId(userId)

        return GetInformationResponse(
            sex = application.sex,
            birthDate = application.birthDate,
            photoUrl = application.photoFileName?.let { applicationPhotoPort.getPhotoUrl(it) },
            applicantName = application.applicantName,
            applicantTel = application.applicantTel,
            parentName = application.ParentName,
            parentTel = application.ParentTel,
            streetAddress = application.streetAddress,
            postalCode = application.postalCode,
            detailAddress = application.detailAddress
        )
    }
}
