package hs.kr.equus.application.domain.school.presentation.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

data class QuerySchoolWebResponse (
  @JsonProperty("content") val content: List<SchoolWebResponse>
)
