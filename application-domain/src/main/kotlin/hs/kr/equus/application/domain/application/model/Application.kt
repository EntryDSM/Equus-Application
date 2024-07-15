package hs.kr.equus.application.domain.application.model

import hs.kr.equus.application.domain.application.exception.ApplicationExceptions
import hs.kr.equus.application.domain.application.model.types.ApplicationRemark
import hs.kr.equus.application.domain.application.model.types.ApplicationType
import hs.kr.equus.application.domain.application.model.types.EducationalStatus
import hs.kr.equus.application.domain.application.model.types.Sex
import hs.kr.equus.application.global.annotation.Aggregate
import java.time.LocalDate
import java.util.*

@Aggregate
data class Application(
    val receiptCode: Long = 0,
    val sex: Sex? = null,
    @get:JvmName("getIsDaejeon")
    val isDaejeon: Boolean? = null,
    @get:JvmName("getIsOutOfHeadcount")
    var isOutOfHeadcount: Boolean? = null,
    val birthDate: LocalDate? = null,
    val photoPath: String? = null,
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
        private val SOCIAL_REMARKS =
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

    fun isRecommendationsRequired(): Boolean = !isEducationalStatusEmpty() && !isCommonApplicationType() && !isProspectiveGraduate();

    fun isCommonApplicationType(): Boolean = applicationType == ApplicationType.COMMON

    fun isFemale(): Boolean = sex == Sex.FEMALE

    fun isMale(): Boolean = sex == Sex.MALE

    fun isSocial() = applicationType == ApplicationType.SOCIAL

    fun isCommon() = applicationType == ApplicationType.COMMON

    fun hasEmptyInfo(): Boolean {
        return listOf(
            sex,
            birthDate,
            applicantTel,
            parentTel,
            detailAddress,
            streetAddress,
            postalCode,
            photoPath,
            applicationType,
            selfIntroduce,
            studyPlan
        ).any { it == null }
    }

    fun isMeister() = applicationType == ApplicationType.MEISTER

    fun isQualificationExam(): Boolean = EducationalStatus.QUALIFICATION_EXAM == educationalStatus

    fun isGraduate(): Boolean = EducationalStatus.GRADUATE == educationalStatus

    fun isProspectiveGraduate(): Boolean = EducationalStatus.PROSPECTIVE_GRADUATE == educationalStatus

    fun isBasicLiving(): Boolean = ApplicationRemark.BASIC_LIVING == applicationRemark

    fun isFromNorth(): Boolean = ApplicationRemark.FROM_NORTH == applicationRemark

    fun isLowestIncome(): Boolean = ApplicationRemark.LOWEST_INCOME == applicationRemark

    fun isMulticultural(): Boolean = ApplicationRemark.MULTICULTURAL == applicationRemark

    fun isOneParent(): Boolean = ApplicationRemark.ONE_PARENT == applicationRemark

    fun isTeenHouseholder(): Boolean = ApplicationRemark.TEEN_HOUSEHOLDER == applicationRemark

    fun isPrivilegedAdmission(): Boolean = ApplicationRemark.PRIVILEGED_ADMISSION == applicationRemark

    fun isNationalMerit(): Boolean = ApplicationRemark.NATIONAL_MERIT == applicationRemark

    fun isProtectedChildren(): Boolean = ApplicationRemark.PROTECTED_CHILDREN == applicationRemark

    fun isEducationalStatusEmpty(): Boolean = this.educationalStatus == null

}
