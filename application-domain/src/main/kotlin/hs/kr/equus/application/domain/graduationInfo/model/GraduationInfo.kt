package hs.kr.equus.application.domain.graduationInfo.model

abstract class GraduationInfo(
    receiptCode: Long,
) {
    abstract fun hasEmptyInfo(): Boolean
    abstract fun isProspectiveGraduate(): Boolean
}
