package hs.kr.equus.application.domain.application.presentation.dto.request

import hs.kr.equus.application.domain.application.model.types.ParentRelation
import hs.kr.equus.application.domain.application.model.types.Sex
import org.hibernate.validator.constraints.Length
import java.time.LocalDate
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

data class UpdateInformationWebRequest(
    @NotNull(message = "sex는 null일 수 없습니다.")
    @Pattern(
        regexp = "^(MALE|FEMALE)$",
        message = "INVALID SEX",
    )
    val sex: Sex,
    @NotNull(message = "birthdate는 Null을 허용하지 않습니다.")
    val birthDate: LocalDate,
    @Length(max = 5, message = "TOO LONG NAME")
    val applicantName: String,
    @Length(max = 11) @Pattern(
        regexp = TEL_REGEXP,
        message = "INVALID TEL",
    )
    val applicantTel: String,
    @Length(max = 11) @Pattern(
        regexp = TEL_REGEXP,
        message = "INVALID TEL",
    )
    val parentTel: String,
    @Length(max = 5, message = "TOO LONG NAME")
    val parentName: String,
    val parentRelation: ParentRelation,
    @NotEmpty(message = "address는 Null 또는 공백을 허용하지 않습니다.") @Length(
        max = 300,
        message = "TOO LONG ADDRESS",
    )
    val streetAddress: String,

    @Length(min = 5, max = 5, message = "INVALID POST_CODE")
    val postalCode: String,

    @NotEmpty(message = "detail_address는 Null 또는 공백을 허용하지 않습니다.") @Length(
        max = 100,
        message = "TOO LONG DETAIL_ADDRESS",
    )
    val detailAddress: String,
) {
    companion object {
        const val TEL_REGEXP = "^\\d{3}\\d{3,4}\\d{4}$"
    }
}
