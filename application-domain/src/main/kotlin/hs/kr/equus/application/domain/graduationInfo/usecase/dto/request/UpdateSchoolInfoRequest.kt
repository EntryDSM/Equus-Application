package hs.kr.equus.application.domain.graduationInfo.usecase.dto.request

import hs.kr.equus.application.domain.graduationInfo.model.vo.StudentNumber

data class UpdateSchoolInfoRequest(
    val studentNumber: StudentNumber,
    val schoolCode: String,
)
