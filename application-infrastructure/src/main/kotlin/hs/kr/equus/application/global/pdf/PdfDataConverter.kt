package hs.kr.equus.application.global.pdf
import hs.kr.equus.application.domain.application.exception.ApplicationExceptions.ApplicationNotFoundException
import hs.kr.equus.application.domain.application.model.Application
import hs.kr.equus.application.domain.application.model.types.EducationalStatus.*
import hs.kr.equus.application.domain.graduationInfo.model.Graduation
import hs.kr.equus.application.domain.graduationInfo.model.GraduationInfo
import hs.kr.equus.application.domain.graduationInfo.spi.QueryGraduationInfoPort
import hs.kr.equus.application.domain.score.model.Score
import hs.kr.equus.application.global.storage.AwsS3Adapter
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.time.LocalDateTime
import java.time.YearMonth
import java.util.*


@Component
class PdfDataConverter(
    private val queryGraduationInfoPort: QueryGraduationInfoPort,
    private val awsS3Adapter: AwsS3Adapter
) {

    private fun setSchoolInfo(application: Application, values: MutableMap<String, Any>) {
        if (!application.isEducationalStatusEmpty() && !application.isQualificationExam()) {
            val graduation =
                queryGraduationInfoPort.queryGraduationInfoByApplication(application)
                    ?: throw Exception("")

            values["schoolCode"] = setBlankIfNull(graduation)
            values["schoolClass"] = setBlankIfNull(application.getSchoolClass())
            values["schoolTel"] = toFormattedPhoneNumber(application.getSchoolTel())
            values["schoolName"] = setBlankIfNull(application.getSchoolName())
        } else {
            values.putAll(emptySchoolInfo())
        }
    }

    private fun setPhoneNumber(application: Application, values: MutableMap<String, Any>) {
        if (application.applicantTel == "00000000000") {
            values["aplicationTel"] = ""
        } else {
            values["applicantTel"] = toFormattedPhoneNumber(application.applicantTel)
        }
        values["parentTel"] = toFormattedPhoneNumber(application.parentTel)
    }

    private fun setGraduationClassification(application: Application, values: MutableMap<String, Any>) {
        values.putAll(emptyGraduationClassification())

        val graduationInfo = queryGraduationInfoPort.queryGraduationInfoByApplication(application)

        val yearMonth = YearMonth.from(graduationInfo!!.graduateDate)

        when (application.educationalStatus!!) {
            QUALIFICATION_EXAM -> {
                values["qualificationExamPassedYear"] = yearMonth.year.toString()
                values["qualificationExamPassedMonth"] = yearMonth.monthValue.toString()
            }

            GRADUATE -> {
                values["graduateYear"] = yearMonth.year.toString()
                values["graduateMonth"] = yearMonth.monthValue.toString()
            }

            PROSPECTIVE_GRADUATE -> {
                values["prospectiveGraduateYear"] = yearMonth.year.toString()
                values["prospectiveGraduateMonth"] = yearMonth.monthValue.toString()
            }
        }
    }


    private fun setUserType(application: Application, values: MutableMap<String, Any>) {
        val list = listOf(
            "isQualificationExam" to application.isQualificationExam(),
            "isGraduate" to application.isGraduate(),
            "isProspectiveGraduate" to application.isProspectiveGraduate(),
            "isDaejeon" to application.isDaejeon,
            "isNotDaejeon" to !application.isDaejeon!!,
            "isBasicLiving" to application.isBasicLiving(),
            "isFromNorth" to application.isFromNorth(),
            "isLowestIncome" to application.isLowestIncome(),
            "isMulticultural" to application.isMulticultural(),
            "isOneParent" to application.isOneParent(),
            "isTeenHouseholder" to application.isTeenHouseholder(),
            "isPrivilegedAdmission" to application.isPrivilegedAdmission(),
            "isNationalMerit" to application.isNationalMerit(),
            "isProtectedChildren" to application.isProtectedChildren(),
            "isCommon" to application.isCommon(),
            "isMeister" to application.isMeister(),
            "isSocialMerit" to application.isSocial()
        )

        list.forEach { (key, value) ->
            values[key] = toBallotBox(value!!)
        }
    }

    private fun setGradeScore(application: Application, score: Score, values: MutableMap<String, Any>) {
        val isQualificationExam = application.isQualificationExam()
        val score1st = if (isQualificationExam) "" else score.thirdBeforeBeforeScore.toString()
        val score2nd = if (isQualificationExam) "" else score.thirdBeforeScore.toString()
        val score3rd = if (isQualificationExam) "" else score.thirdGradeScore.toString()

        with(values) {
            put("conversionScore1st", score1st)
            put("conversionScore2nd", score2nd)
            put("conversionScore3rd", score3rd)
            put("conversionScore", score.totalGradeScore.toString())
            put("attendanceScore", score.attendanceScore.toString())
            put("volunteerScore", score.volunteerScore.toString())
            put("finalScore", score.totalScore.toString())
        }
    }


    private fun setLocalDate(values: MutableMap<String, Any>) {
        val now: LocalDateTime = LocalDateTime.now()
        with(values) {
            put("year", java.lang.String.valueOf(now.year))
            put("month", java.lang.String.valueOf(now.monthValue))
            put("day", java.lang.String.valueOf(now.dayOfMonth))
        }
    }

    private fun setIntroduction(application: Application, values: MutableMap<String, Any>) {
        values["selfIntroduction"] = setBlankIfNull(application.selfIntroduce)
        values["studyPlan"] = setBlankIfNull(application.studyPlan)
        values["newLineChar"] = "\n"
    }

    private fun setParentInfo(application: Application, values: MutableMap<String, Any>) {
        values["parentName"] = application.parentName!!
    }

    private fun setRecommendations(application: Application, values: MutableMap<String, Any>) {
        values["isDaejeonAndMeister"] = markIfTrue(application.isDaejeon!! || application.isMeister())
        values["isDaejeonAndSocialMerit"] = markIfTrue(application.isDaejeon!! && application.isSocial())
        values["isNotDaejeonAndMeister"] = markIfTrue(!application.isDaejeon!! && application.isMeister())
        values["isNotDaejeonAndSocialMerit"] =
            markIfTrue(!application.isDaejeon!! && application.isSocial())
    }

    private fun setBase64Image(application: Application, values: MutableMap<String, Any>) {
        val imageBytes: ByteArray = awsS3Adapter.getObject(application.photoPath!!, "entry_photo/")
        val base64EncodedImage = String(Base64.getEncoder().encode(imageBytes), StandardCharsets.UTF_8)
        values["base64Image"] = base64EncodedImage
    }

    private fun markIfTrue(isTrue: Boolean): String {
        return if (isTrue) "◯" else ""
    }

    private fun emptySchoolInfo(): Map<String, Any> {
        return java.util.Map.of<String, Any>(
            "schoolCode", "",
            "schoolClass", "",
            "schoolTel", "",
            "schoolName", ""
        )
    }


    private fun emptyGraduationClassification(): Map<String, Any> {
        return java.util.Map.of<String, Any>(
            "qualificationExamPassedYear", "20__",
            "qualificationExamPassedMonth", "__",
            "graduateYear", "20__",
            "graduateMonth", "__",
            "prospectiveGraduateYear", "20__",
            "prospectiveGraduateMonth", "__"
        )
    }

    private fun toFormattedPhoneNumber(phoneNumber: String?): String {
        if (phoneNumber.isNullOrBlank()) {
            return ""
        }
        if (phoneNumber.length == 8) {
            return phoneNumber.replace("(\\d{4})(\\d{4})".toRegex(), "$1-$2")
        }
        return phoneNumber.replace("(\\d{2,3})(\\d{3,4})(\\d{4})".toRegex(), "$1-$2-$3")
    }

    private fun setBlankIfNull(input: String?): String {
        return input ?: ""
    }

    private fun toBallotBox(`is`: Boolean): String {
        return if (`is`) "☑" else "☐"
    }
}