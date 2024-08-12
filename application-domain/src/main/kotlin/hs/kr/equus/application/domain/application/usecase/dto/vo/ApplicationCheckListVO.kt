package hs.kr.equus.application.domain.application.usecase.dto.vo

import hs.kr.equus.application.domain.application.model.types.ApplicationRemark
import hs.kr.equus.application.domain.application.model.types.ApplicationType
import hs.kr.equus.application.domain.application.model.types.EducationalStatus
import hs.kr.equus.application.domain.application.model.types.Sex
import hs.kr.equus.application.domain.applicationCase.model.vo.ExtraScoreItem
import java.time.LocalDate
import java.time.Year

data class ApplicationCheckListVO (
    val receiptCode: Long,
    val applicationType: ApplicationType,
    val isDaejeon: Boolean,
    val applicationRemark: ApplicationRemark,
    val applicationName: String,
    val birthDate: LocalDate,
    val address: String,
    val applicantTel: String,
    val sex: Sex,
    val educationalStatus: EducationalStatus,
    val graduationYear: Year,
    val school: String,
    val classNumber: String,
    val parentName: String,
    val parentTel: String,
    val koreanGrade: String,
    val socialGrade: String,
    val historyGrade: String,
    val mathGrade: String,
    val scienceGrade: String,
    val englishGrade: String,
    val techAndHomeGrade: String,
    val latenessCount: Int,
    val earlyLeaveCount: Int,
    val extraScoreItem: ExtraScoreItem,
    val absenceDayCount: Int,
    val lectureAbsenceCount: Int,
)
