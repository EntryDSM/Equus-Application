package hs.kr.equus.application.domain.application.usecase.dto.request

import hs.kr.equus.application.domain.application.exception.ApplicationExceptions

data class GetApplicantsRequest(
    val receiptCode: String?,
    val name: String?,
    val schoolName: String?,
    val isDaejeon: Boolean,
    val isNationwide: Boolean,
    val isCommon: Boolean,
    val isMeister: Boolean,
    val isSocial: Boolean,
    val isOutOfHeadcount: Boolean,
    val isSubmitted: Boolean,
    val isNotSubmitted: Boolean
) {
    val formattedReceiptCode: String
        get() = receiptCode?.replace(" ", "")?.takeIf { it.isNotEmpty() }
            ?.let {
                try {
                    "${it.toLong()}%"
                } catch (e: NumberFormatException) {
                    throw ApplicationExceptions.InvalidKeywordException()
                }
            } ?: ""

    val defaultName: String
        get() = name ?: ""

    val defaultSchoolName: String
        get() = schoolName ?: ""

    val isDaejeonQuery: Boolean?
        get() = if (isDaejeon == isNationwide) null else isDaejeon

    val isOutOfHeadcountQuery: Boolean?
        get() = if (isOutOfHeadcount) true else null

    val isSubmittedQuery: Boolean?
        get() = if (isSubmitted == isNotSubmitted) null else isSubmitted

    val isCommonQuery: Boolean
        get() = if (!isCommon && !isMeister && !isSocial) true else isCommon

    val isMeisterQuery: Boolean
        get() = if (!isCommon && !isMeister && !isSocial) true else isMeister

    val isSocialQuery: Boolean
        get() = if (!isCommon && !isMeister && !isSocial) true else isSocial
}
