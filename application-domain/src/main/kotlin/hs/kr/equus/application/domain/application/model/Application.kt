package hs.kr.equus.application.domain.application.model

import hs.kr.equus.application.domain.application.exception.ApplicationExceptions
import hs.kr.equus.application.domain.application.model.types.ApplicationRemark
import hs.kr.equus.application.domain.application.model.types.ApplicationType
import hs.kr.equus.application.domain.application.model.types.EducationalStatus
import hs.kr.equus.application.domain.application.model.types.Sex
import hs.kr.equus.application.global.annotation.Aggregate
import java.time.LocalDate
import java.util.UUID

@Aggregate
data class Application(
    val receiptCode: Long? = null,
    val sex: Sex? = null,
    val isDaejeon: Boolean? = null,
    var isOutOfHeadcount: Boolean? = null,
    val birthDate: LocalDate? = null,
    val photoFileName: String? = null,
    val educationalStatus: EducationalStatus? = null,
    val applicantName: String? = null,
    val applicantTel: String? = null,
    val parentName: String? = null,
    val parentTel: String? = null,
    val streetAddress: String? = null,
    val postalCode: String? = null,
    val detailAddress: String? = null,
    val applicationType: ApplicationType? = null,
    val applicationRemark: ApplicationRemark? = null,
    val studyPlan: String? = null,
    val selfIntroduce: String? = null,
    val userId: UUID,
) {
    companion object {
        const val DEFAULT_TEL = "010-0000-0000"
        val SOCIAL_REMARKS =
            listOf(
                ApplicationRemark.ONE_PARENT,
                ApplicationRemark.FROM_NORTH,
                ApplicationRemark.MULTICULTURAL,
                ApplicationRemark.BASIC_LIVING,
                ApplicationRemark.LOWEST_INCOME,
                ApplicationRemark.TEEN_HOUSEHOLDER,
                ApplicationRemark.PROTECTED_CHILDREN,
            )
    }

    init {
        if (checkSocialSelectOtherRemark() || checkNotSocialSelectSocialRemark()) {
            throw ApplicationExceptions.InvalidApplicationRemarkException()
        }
    }

    private fun checkSocialSelectOtherRemark(): Boolean = isSocial() && applicationRemark !in SOCIAL_REMARKS

    private fun checkNotSocialSelectSocialRemark() = !isSocial() && applicationRemark in SOCIAL_REMARKS

    fun isSocial() = applicationType == ApplicationType.SOCIAL
}
