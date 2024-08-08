package hs.kr.equus.application.domain.applicationCase.domain.entity

import hs.kr.equus.application.domain.applicationCase.domain.entity.vo.ExtraScoreItem
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "tbl_graduation_case")
class GraduationCaseJpaEntity(
    override val id: Long,
    val volunteerTime: Int,
    val absenceDayCount: Int,
    val lectureAbsenceCount: Int,
    val latenessCount: Int,
    val earlyLeaveCount: Int,
    val koreanGrade: String,
    val socialGrade: String,
    val historyGrade: String,
    val mathGrade: String,
    val scienceGrade: String,
    val englishGrade: String,
    val techAndHomeGrade: String,
    @get:JvmName("getIsProspectiveGraduate")
    val isProspectiveGraduate: Boolean,
    override val receiptCode: Long,
    @Embedded
    override val extraScoreItem: ExtraScoreItem,
) : ApplicationCaseEntity(
    id = id,
    receiptCode = receiptCode,
    extraScoreItem = extraScoreItem
)