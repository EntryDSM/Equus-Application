package hs.kr.equus.application.domain.application.spi

import hs.kr.equus.application.domain.application.model.Applicant
import hs.kr.equus.application.domain.application.model.Application
import hs.kr.equus.application.domain.application.model.types.ApplicationType
import hs.kr.equus.application.domain.application.spi.dto.PagedResult
import hs.kr.equus.application.domain.application.usecase.dto.response.GetApplicationCountResponse
import java.util.*

interface QueryApplicationPort {
    fun queryApplicationByUserId(userId: UUID): Application?

    fun isExistsApplicationByUserId(userId: UUID): Boolean

    fun queryApplicationCountByApplicationTypeAndIsDaejeon(
        applicationType: ApplicationType,
        isDaejeon: Boolean,
    ): GetApplicationCountResponse

    fun queryApplicationByReceiptCode(receiptCode: Long): Application?

    fun queryAllApplicantsByFilter(
        schoolName: String,
        name: String,
        isDaejeon: Boolean?,
        isOutOfHeadcount: Boolean?,
        isCommon: Boolean,
        isMeister: Boolean,
        isSocial: Boolean,
        isSubmitted: Boolean?,
        pageSize: Long,
        offset: Long
    ): PagedResult<Applicant>
}
