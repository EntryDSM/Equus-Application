package hs.kr.equus.application.domain.application.presentation.dto.request

import org.hibernate.validator.constraints.Length

data class UpdateIntroduceWebRequest(
    @Length(max = 1600, message = "content는 최대 1,600글자까지만 허용됩니다.")
    val content: String,
)
