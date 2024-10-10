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
    @Column(precision = 6, scale = 3)
    val volunteerScore: BigDecimal?,
    @Column(precision = 6, scale = 3)
    val thirdBeforeBeforeScore: BigDecimal?,
    @Column(precision = 6, scale = 3)
    val thirdBeforeScore: BigDecimal?,
    @Column(precision = 6, scale = 3)
    val thirdGradeScore: BigDecimal?,
    @Column(precision = 6, scale = 3)
    val thirdScore: BigDecimal?,
    @Column(precision = 6, scale = 3)
    val totalGradeScore: BigDecimal?,
    val extraScore: Int?,
    @Column(precision = 6, scale = 3)
    val totalScore: BigDecimal?,
    @Column(unique = true)
    val receiptCode: Long,
)