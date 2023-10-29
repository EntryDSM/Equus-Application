package hs.kr.equus.application.domain.graduationInfo.model

import hs.kr.equus.application.domain.application.model.types.EducationalStatus
import java.time.LocalDate

data class Qualification(
    val receiptCode: Long,
    val qualifiedDate: LocalDate, //TODO graduateDate로 통합할 예정->다형성 적용
    val educationalStatus: EducationalStatus, //TODO 삭제할 예정
) : GraduationInfo(receiptCode) {
    override fun hasEmptyInfo(): Boolean = qualifiedDate == null
    //TODO 기존 로직이랑 꼬일까봐 일단 오버라이딩 함. 1차 구현하고 수정할예정
    override fun isProspectiveGraduate(): Boolean = false
}
