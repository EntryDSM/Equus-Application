package hs.kr.equus.application.domain.application.service

import hs.kr.equus.application.domain.application.model.types.ApplicationRemark
import hs.kr.equus.application.domain.application.model.types.ApplicationType
import hs.kr.equus.application.domain.application.model.types.EducationalStatus
import hs.kr.equus.application.domain.application.model.types.Sex
import hs.kr.equus.application.global.annotation.DomainService
import java.math.BigDecimal

@DomainService
class ApplicationService {
     fun translateEducationalStatus(status: EducationalStatus?): String {
        return when(status) {
            EducationalStatus.PROSPECTIVE_GRADUATE -> "졸업예정자"
            EducationalStatus.GRADUATE -> "졸업"
            EducationalStatus.QUALIFICATION_EXAM -> "검정고시"
            else -> "X"
        }
    }

     fun translateIsDaejeon(isDaejeon: Boolean?): String {
        return when(isDaejeon) {
            true -> "대전"
            false -> "전국"
            else -> "X"
        }
    }
     fun translateApplicationType(type: ApplicationType?): String {
        return when(type) {
            ApplicationType.COMMON -> "일반전형"
            ApplicationType.MEISTER -> "마이스터인재전형"
            ApplicationType.SOCIAL -> "사회특별전형"
            else -> "X"
        }
    }

     fun translateSex(sex: Sex?): String {
        return when(sex) {
            Sex.MALE -> "남"
            Sex.FEMALE -> "여"
            else -> "X"
        }
    }

    fun translateBoolean(boolean: Boolean?): String {
        return when(boolean) {
            true -> "O"
            else -> "X"
        }
    }

     fun translateApplicationRemark(remark: ApplicationRemark?): String {
        return when(remark) {
            ApplicationRemark.ONE_PARENT -> "한부모가족"
            ApplicationRemark.FROM_NORTH -> "북한이탈주민"
            ApplicationRemark.MULTICULTURAL -> "다문화가족"
            ApplicationRemark.BASIC_LIVING -> "기초생활수급자"
            ApplicationRemark.LOWEST_INCOME -> "차상위계층"
            ApplicationRemark.TEEN_HOUSEHOLDER -> "소년소녀가장"
            ApplicationRemark.PRIVILEGED_ADMISSION -> "특례입학대상자"
            ApplicationRemark.NATIONAL_MERIT -> "국가유공자"
            ApplicationRemark.PROTECTED_CHILDREN -> "보호대상아동"
            null -> "일반전형"
        }
    }

    fun safeGetValue(value: Any?): String = value?.toString() ?: "X"

    fun safeGetDouble(value: Any?): Any = when (value) {
        is Double -> value
        is Int -> value.toDouble()
        is BigDecimal -> value.toDouble()
        null -> ""
        else -> 0.0
    }

    fun formatPhoneNumber(phoneNumber: String?): String {
        return "${phoneNumber!!.substring(0, 3)}-${phoneNumber.substring(3, 7)}-${phoneNumber.substring(7, 11)}"
    }

}
