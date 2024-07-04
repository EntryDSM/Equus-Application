package hs.kr.equus.application.domain.school.presentation.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class SchoolWebResponse(
    @JsonProperty("code") val code: String,
    @JsonProperty("name") val name: String,
    @JsonProperty("information") val information: String,
    @JsonProperty("address") val address: String
)
