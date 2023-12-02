package hs.kr.equus.application.domain.application.domain.entity

import hs.kr.equus.application.domain.application.model.types.ApplicationRemark
import hs.kr.equus.application.domain.application.model.types.ApplicationType
import hs.kr.equus.application.domain.application.model.types.EducationalStatus
import hs.kr.equus.application.domain.application.model.types.Sex
import java.time.LocalDate
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table
import javax.validation.constraints.NotNull

@Entity
@Table(name = "tbl_application")
class ApplicationJpaEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val receiptCode: Long = 0,
    @Enumerated(EnumType.STRING)
    val sex: Sex?,
    @get:JvmName("getIsDaejeon")
    val isDaejeon: Boolean?,
    @get:JvmName("getIsOutOfHeadcount")
    var isOutOfHeadcount: Boolean?,
    val birthDate: LocalDate?,
    val photoPath: String?,
    @Enumerated(EnumType.STRING)
    val educationalStatus: EducationalStatus?,
    val applicantName: String?,
    @Column(columnDefinition = "char(11)")
    val applicantTel: String?,
    val parentName: String?,
    @Column(columnDefinition = "char(11)")
    val parentTel: String?,
    val streetAddress: String?,
    val postalCode: String?,
    val detailAddress: String?,
    @Enumerated(EnumType.STRING)
    val applicationType: ApplicationType?,
    @Enumerated(EnumType.STRING)
    val applicationRemark: ApplicationRemark?,
    val studyPlan: String?,
    val selfIntroduce: String?,
    @field:NotNull
    @Column(unique = true, columnDefinition = "BINARY(16)")
    val userId: UUID,
)
