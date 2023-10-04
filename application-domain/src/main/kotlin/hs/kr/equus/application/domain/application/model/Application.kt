package hs.kr.equus.application.domain.application.model

import hs.kr.equus.application.domain.application.model.types.EducationalStatus
import hs.kr.equus.application.domain.application.model.types.Sex
import hs.kr.equus.application.domain.application.model.vo.*
import hs.kr.equus.application.global.annotation.domain.Aggregate
import java.util.UUID

@Aggregate
data class Application(
    val receiptCode: UUID = UUID(0, 0),
    val sex: Sex?,
    val isDaejeon: Boolean?,
    val address: Address?,
    val applicantType: ApplicantType?,
    val applicantInfo: ApplicantInfo?,
    val parentInfo: ParentInfo?,
    val educationalStatus: EducationalStatus?,
    val studyPlan: String?,
    val selfIntroduce: String?,
    val userId: UUID,
)