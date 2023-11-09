package hs.kr.equus.application.domain.graduationInfo.domain.entity

import hs.kr.equus.application.global.entity.BaseTimeEntity
import java.time.LocalDate
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass
import javax.validation.constraints.NotNull

@MappedSuperclass
abstract class GraduationInfoEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    val graduateDate: LocalDate,
    @get:JvmName("getIsProspectiveGraduate")
    val isProspectiveGraduate: Boolean,
    @NotNull
    val receiptCode: Long,
) : BaseTimeEntity()
