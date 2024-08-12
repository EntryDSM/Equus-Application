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
    val koreanGrade: String = "XXXX",
    val socialGrade: String = "XXXX",
    val historyGrade: String = "XXXX",
    val mathGrade: String = "XXXX",
    val scienceGrade: String = "XXXX",
    val englishGrade: String = "XXXX",
    val techAndHomeGrade: String = "XXXX",
    val latenessCount: Int,
    val earlyLeaveCount: Int,
    val extraScoreItem: ExtraScoreItem,
    val absenceDayCount: Int,
    val lectureAbsenceCount: Int,
    val totalScore3rdYear: Int,  // 총합 점수: 3학년 성적 총합
    val lastSemesterTotal: Int,  // 직전 학기 성적 총합
    val beforeLastSemesterTotal: Int,  // 직전전 학기 성적 총합
    val convertedScore: Int,  // 교과성적환산점수
    val volunteerHours: Int,  // 봉사시간
    val volunteerScore: Int,  // 봉사점수
    val result: String,  // 결과
    val attendanceScore: Int,  // 출석점수
    val competition: String,  // 대회
    val certification: String,
    val totalScore1stSelection: Int
)
