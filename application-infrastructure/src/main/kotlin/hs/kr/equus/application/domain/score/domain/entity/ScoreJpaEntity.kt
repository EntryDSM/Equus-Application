package hs.kr.equus.application.domain.score.domain.entity

import java.math.BigDecimal
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "tbl_score")
class ScoreJpaEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long,
    val attendanceScore: Int?,
    val volunteerScore: BigDecimal?,
    val thirdBeforeBeforeScore: BigDecimal?,
    val thirdBeforeScore: BigDecimal?,
    val thirdGradeScore: BigDecimal?,
    val thirdScore: BigDecimal?,
    val totalGradeScore: BigDecimal?,
    val extraScore: Int?,
    val totalScore: BigDecimal?,
    @Column(unique = true)
    val receiptCode: Long,
)