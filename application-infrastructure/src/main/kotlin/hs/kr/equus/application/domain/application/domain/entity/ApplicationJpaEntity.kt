package hs.kr.equus.application.domain.application.domain.entity

import hs.kr.equus.application.domain.application.model.types.*
import java.time.LocalDate
import java.util.*
import javax.persistence.*

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
    @Column(columnDefinition = "TEXT")
    val photoPath: String?,
    @Enumerated(EnumType.STRING)
    val educationalStatus: EducationalStatus?,
    val applicantName: String?,
    @Column(columnDefinition = "char(11)")
    val applicantTel: String?,
    val parentName: String?,
    @Column(columnDefinition = "char(11)")
    val parentTel: String?,
    val parentRelation: String?,
    val streetAddress: String?,
    val postalCode: String?,
    val detailAddress: String?,
    @Enumerated(EnumType.STRING)
    val applicationType: ApplicationType?,
    @Enumerated(EnumType.STRING)
    val applicationRemark: ApplicationRemark?,
    val studyPlan: String?,
    val selfIntroduce: String?,
//    @field:NotNull
    @Column(unique = true, columnDefinition = "BINARY(16)")
    val userId: UUID,
    val veteransNumber: Int?
)
