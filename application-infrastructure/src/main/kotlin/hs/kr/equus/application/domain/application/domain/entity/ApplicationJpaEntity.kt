package hs.kr.equus.application.domain.application.domain.entity

import hs.kr.equus.application.domain.application.model.types.ApplicationRemark
import hs.kr.equus.application.domain.application.model.types.ApplicationType
import hs.kr.equus.application.domain.application.model.types.EducationalStatus
import hs.kr.equus.application.domain.application.model.types.Sex
import java.time.LocalDate
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "tbl_application")
class ApplicationJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val receiptCode: Long,
    @Enumerated(EnumType.STRING)
    val sex: Sex? = null,
    val isDaejeon: Boolean? = null,
    var isOutOfHeadcount: Boolean? = null,
    val birthDate: LocalDate? = null,
    val photoFileName: String? = null,
    @Enumerated(EnumType.STRING)
    val educationalStatus: EducationalStatus? = null,
    val applicantName: String? = null,
    @Column(columnDefinition = "char(11)")
    val applicantTel: String? = null,
    val parentName: String? = null,
    @Column(columnDefinition = "char(11)")
    val parentTel: String? = null,
    val streetAddress: String? = null,
    val postalCode: String? = null,
    val detailAddress: String? = null,
    @Enumerated(EnumType.STRING)
    val applicationType: ApplicationType? = null,
    @Enumerated(EnumType.STRING)
    val applicationRemark: ApplicationRemark? = null,
    val studyPlan: String? = null,
    val selfIntroduce: String? = null,
    @field:NotNull
    @Column(unique = true)
    val userId: UUID,
)
