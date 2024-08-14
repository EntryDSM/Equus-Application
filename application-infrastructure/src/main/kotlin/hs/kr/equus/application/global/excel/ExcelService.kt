package hs.kr.equus.application.global.excel

import hs.kr.equus.application.domain.application.model.types.ApplicationRemark
import hs.kr.equus.application.domain.application.model.types.ApplicationType
import hs.kr.equus.application.domain.application.model.types.EducationalStatus
import hs.kr.equus.application.domain.application.model.types.Sex
import org.springframework.stereotype.Component

@Component
class ExcelService {
     fun translateEducationalStatus(status: EducationalStatus?): String {
        return when(status) {
            EducationalStatus.PROSPECTIVE_GRADUATE -> "졸업예정"
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
            null -> "X"
            else -> "해당 없음"
        }
    }

    fun safeGetValue(value: Any?): String = value?.toString() ?: "X"
}