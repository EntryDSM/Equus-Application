package hs.kr.equus.application.domain.graduationInfo.presentation.dto.request

import org.hibernate.validator.constraints.Length
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class UpdateGraduationInformationWebRequest(
    @Length(
        max = 1,
        message = "grade_number는 1자 이하여야 합니다.",
    ) @Pattern(
        regexp = NUMERIC_REGEXP,
        message = "grade_number는 숫자여야합니다.",
    )
    val gradeNumber: String,
    @Length(
        max = 2,
        message = "class_number는 2자 이하여야 합니다.",
    ) @Pattern(
        regexp = NUMERIC_REGEXP,
        message = "class_number는 숫자여야합니다.",
    )
    val classNumber: String,
    @Length(
        max = 2,
        message = "student_number는 2자 이하여야 합니다.",
    ) @Pattern(
        regexp = NUMERIC_REGEXP,
        message = "student_number는 숫자여야합니다.",
    )
    val studentNumber: String,
    @NotBlank(message = "school_code는 Null, 공백, 띄어쓰기를 허용하지 않습니다.")
    val schoolCode: String,

    @NotBlank(message = "teacher_name Null, 공백, 띄어쓰기를 허용하지 않습니다.",)
    @Size(max = 4)
    val teacherName: String,

    @NotBlank(message  = "teacher_tel Null, 공백, 띄어쓰기를 허용하지 않습니다.")
    val teacherTel: String
) {
    companion object {
        const val NUMERIC_REGEXP = "^\\d{1,5}"
    }
}
