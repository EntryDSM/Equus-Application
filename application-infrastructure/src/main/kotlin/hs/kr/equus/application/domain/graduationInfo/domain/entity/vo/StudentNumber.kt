package hs.kr.equus.application.domain.graduationInfo.domain.entity.vo

import javax.persistence.Embeddable

@Embeddable
class StudentNumber(
    var gradeNumber: String,
    var classNumber: String,
    var studentNumber: String,
)