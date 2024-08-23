package hs.kr.equus.application.domain.application.usecase.dto.request

data class GetApplicantsRequest(
    val name: String? = "",
    val schoolName: String? = "",
    var isDaejeon: Boolean? = false,
    var isNationwide: Boolean? = false,
    var isCommon: Boolean = false,
    var isMeister: Boolean = false,
    var isSocial: Boolean = false,
    val isOutOfHeadcount: Boolean = false,
    var isSubmitted: Boolean? = false,
    var isNotSubmitted: Boolean = false
) {
    init {
        if (!isCommon && !isMeister && !isSocial) {
            isCommon = true
            isMeister = true
            isSocial = true
        }

        if(!isSubmitted!! && !isNotSubmitted) {
            isSubmitted = null
        }

        if(!isDaejeon!! && !isNationwide!!) {
            isDaejeon = null
            isNationwide = null
        }
    }
}
