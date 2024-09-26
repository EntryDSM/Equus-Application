package hs.kr.equus.application.domain.application.usecase

import hs.kr.equus.application.domain.application.spi.QueryApplicationPort
import hs.kr.equus.application.domain.application.usecase.dto.request.GetApplicantsRequest
import hs.kr.equus.application.domain.application.usecase.dto.response.ApplicantDto
import hs.kr.equus.application.domain.application.usecase.dto.response.GetApplicantsResponse
import hs.kr.equus.application.global.annotation.ReadOnlyUseCase
import kotlin.math.ceil

@ReadOnlyUseCase
class GetApplicantsUseCase(
    private val queryApplicationPort: QueryApplicationPort
) {
    fun execute(pageSize: Long, offset: Long, getApplicantsRequest: GetApplicantsRequest): GetApplicantsResponse{
        val pagedApplicants = getApplicantsRequest.run {
            queryApplicationPort.queryAllApplicantsByFilter(
                schoolName = schoolName!!,
                name = name!!,
                isDaejeon = if (isDaejeon == null && isNationwide != null) false else isDaejeon,
                isOutOfHeadcount = isOutOfHeadcount,
                isCommon = isCommon,
                isMeister = isMeister,
                isSocial = isSocial,
                isSubmitted = isSubmitted,
                pageSize = pageSize,
                offset = offset
            )
        }

        val applicantDtoList = pagedApplicants.items.map {
            ApplicantDto(
                receiptCode = it.receiptCode,
                name = it.name,
                telephoneNumber = it.telephoneNumber,
                isDaejeon = it.isDaejeon,
                applicationType = it.applicationType,
                isSubmitted = it.isSubmitted,
                isOutOfHeadcount = it.isOutOfHeadcount,
                isPrintsArrived = it.isPrintsArrived
            )
        }

        return GetApplicantsResponse(applicantDtoList, pagedApplicants.hasNextPage, pagedApplicants.totalSize )
    }
}