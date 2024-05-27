package hs.kr.equus.application.domain.school.model

import hs.kr.equus.application.domain.school.exception.SchoolExceptions
import hs.kr.equus.application.global.annotation.Aggregate

@Aggregate
data class School(
    val code: String,
    val name: String,
    val tel: String,
    val type: String,
    val address: String
) {
    init {
        check(type == "중학교") {
            throw SchoolExceptions.InvalidSchoolTypeException()
        }
    }
}
