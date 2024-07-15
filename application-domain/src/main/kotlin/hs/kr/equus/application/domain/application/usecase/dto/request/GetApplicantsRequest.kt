package hs.kr.equus.application.domain.application.usecase.dto.request

import hs.kr.equus.application.domain.application.exception.ApplicationExceptions

data class GetApplicantsRequest(
    val name: String? = "",
    val schoolName: String? = "",
    val isDaejeon: Boolean? = null,
    val isNationwide: Boolean? = null,
    var isCommon: Boolean = false,
    var isMeister: Boolean = false,
    var isSocial: Boolean = false,
    val isOutOfHeadcount: Boolean? = null,
    val isSubmitted: Boolean? = null,
    val isNotSubmitted: Boolean? = null
) {
    init {
        if (!isCommon && !isMeister && !isSocial) {
            isCommon = true
            isMeister = true
            isSocial = true
        }
    }
}
