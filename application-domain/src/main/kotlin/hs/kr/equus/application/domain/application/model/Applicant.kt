package hs.kr.equus.application.domain.application.model

import hs.kr.equus.application.global.annotation.Aggregate

data class Applicant(
    val receiptCode: Long,
    val name: String?,
    val telephoneNumber: String?,
    val isDaejeon: Boolean?,
    val applicationType: String?,
    val isPrintsArrived: Boolean?,
    val isSubmitted: Boolean?,
    val isOutOfHeadcount: Boolean?
)
