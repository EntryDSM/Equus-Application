package hs.kr.equus.application.global.util.pdf

import hs.kr.equus.application.domain.application.model.Application
import hs.kr.equus.application.domain.application.model.types.EducationalStatus.*
import hs.kr.equus.application.domain.file.spi.GetObjectPort
import hs.kr.equus.application.domain.file.usecase.`object`.PathList
import hs.kr.equus.application.domain.graduationInfo.exception.GraduationInfoExceptions
import hs.kr.equus.application.domain.graduationInfo.model.Graduation
import hs.kr.equus.application.domain.graduationInfo.spi.GraduationInfoQuerySchoolPort
import hs.kr.equus.application.domain.graduationInfo.spi.QueryGraduationInfoPort
import hs.kr.equus.application.domain.school.exception.SchoolExceptions
import hs.kr.equus.application.domain.score.model.Score
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.YearMonth
import java.util.*


@Component
class PdfDataConverter(
    private val queryGraduationInfoPort: QueryGraduationInfoPort,
    private val getObjectPort: GetObjectPort,
    private val graduationInfoQuerySchoolPort: GraduationInfoQuerySchoolPort
) {
    fun applicationToInfo(application: Application, score: Score?): PdfData {
        val values: MutableMap<String, Any> = HashMap()
        setReceiptCode(application, values)
        setEntranceYear(values)
        setPersonalInfo(application, values)
        setGenderInfo(application, values)
        setSchoolInfo(application, values)
        setPhoneNumber(application, values)
        setGraduationClassification(application, values)
        setUserType(application, values)
        setGradeScore(application, score!!, values)
        setLocalDate(values)
        setIntroduction(application, values)
        setParentInfo(application, values)

        if (application.isRecommendationsRequired()) {
            setRecommendations(application, values)
        }

        if (!application.photoPath.isNullOrBlank()
        ) {
            setBase64Image(application, values)
        }

        return PdfData(values)
    }

    private fun setReceiptCode(application: Application, values: MutableMap<String, Any>) {
        values["receiptCode"] = application.receiptCode
    }

    private fun setEntranceYear(values: MutableMap<String, Any>) {
        val entranceYear: Int = LocalDate.now().plusYears(1).year
        values["entranceYear"] = entranceYear.toString()
    }

    private fun setPersonalInfo(application: Application, values: MutableMap<String, Any>) {
        val name = application.applicantName
        values["userName"] = setBlankIfNull(name)
        values["isMale"] = toBallotBox(application.isMale())
        values["isFemale"] = toBallotBox(application.isFemale())
        values["address"] = setBlankIfNull(application.streetAddress)
        values["detailAddress"] = setBlankIfNull(application.detailAddress)
    }

    private fun setGenderInfo(application: Application, values: MutableMap<String, Any>) {
        var gender: String? = null
        if (application.isFemale()){ gender = "여" }
        else if (application.isMale()){ gender = "남" }
        values["gender"] = setBlankIfNull(gender)
    }

    private fun setSchoolInfo(application: Application, values: MutableMap<String, Any>) {
        if (!application.isEducationalStatusEmpty() && !application.isQualificationExam()) {
            val graduation =
                queryGraduationInfoPort.queryGraduationInfoByApplication(application)
                    ?: throw Exception("")
            if (graduation !is Graduation) throw GraduationInfoExceptions.EducationalStatusUnmatchedException()



            val school = graduationInfoQuerySchoolPort.querySchoolBySchoolCode(graduation.schoolCode!!)
                ?: throw SchoolExceptions.SchoolNotFoundException()

            values["schoolCode"] = setBlankIfNull(school.code)
            values["schoolClass"] = setBlankIfNull(graduation.studentNumber!!.classNumber)
            values["schoolTel"] = toFormattedPhoneNumber(school.tel)
            values["schoolName"] = setBlankIfNull(school.name)
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

        val yearMonth = graduationInfo?.graduateDate?.let { YearMonth.from(it) } ?: YearMonth.now()

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
        val imageBytes: ByteArray = getObjectPort.getObject(application.photoPath!!, PathList.PHOTO)
        val base64EncodedImage = Base64.getEncoder().encodeToString(imageBytes)
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