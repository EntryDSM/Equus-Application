package hs.kr.equus.application.domain.application.model

import hs.kr.equus.application.domain.application.model.types.ApplicationRemark
import hs.kr.equus.application.domain.application.model.types.ApplicationType
import hs.kr.equus.application.domain.application.model.types.Sex
import hs.kr.equus.application.global.annotation.domain.Aggregate
import java.time.LocalDate
import java.util.*

@Aggregate
data class Application(
    val receiptCode: Long,
    val sex: Sex? = null,
    val isDaejeon: Boolean? = null,
    var isOutOfHeadcount: Boolean? = null,
    val birthDate: LocalDate? = null,
    val imageFileName: String? = null,
    val applicantName: String? = null,
    val applicantTel: String? = null,
    val ParentName: String? = null,
    val ParentTel: String? = null,
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
    }

    init {
        if (!isSocial() && applicationRemark != null)
            throw IllegalArgumentException("사회통합전형 전용 사항입니다")

        if (isSocial() && applicationRemark == null)
            throw IllegalArgumentException("세부사항을 입력해주세요")
    }

    fun isSocial() = applicationType == ApplicationType.SOCIAL
}
