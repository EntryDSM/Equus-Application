package hs.kr.equus.application.domain.application.model.vo

import hs.kr.equus.application.domain.application.model.types.ApplicationRemark
import hs.kr.equus.application.domain.application.model.types.ApplicationType

data class ApplicantType(
    val applicationType: ApplicationType,
    val applicationRemark: ApplicationRemark?,
) {
    init {
        checkIsNotSocialSelectRemark()
        checkIsSocialNotSelectRemark()
    }

    private fun checkIsNotSocialSelectRemark() =
        require(!isSocial() && applicationRemark == null) {
            throw IllegalArgumentException("사회통합전형 전용 사항입니다")
        }

    private fun checkIsSocialNotSelectRemark() =
        require(isSocial() && applicationRemark != null) {
            throw IllegalArgumentException("세부사항을 입력해주세요")
        }

    fun isCommon(): Boolean = this.applicationType == ApplicationType.COMMON

    private fun isSocial(): Boolean = this.applicationType == ApplicationType.SOCIAL
}
