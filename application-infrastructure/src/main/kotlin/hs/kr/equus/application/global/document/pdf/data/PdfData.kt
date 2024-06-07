package hs.kr.equus.application.global.document.pdf.data

class PdfData(
    val data: MutableMap<String, Any>? = null
) {

    fun toMap(): MutableMap<String, Any>? {
        return data
    }

    fun getReceiptCode(): Long {
        return data!!["receiptCode"] as Long
    }

    fun getEntranceYear(): String? {
        return data!!["entranceYear"] as String?
    }

    fun getUserName(): String? {
        return data!!["userName"] as String?
    }

    fun getIsMale(): String? {
        return data!!["isMale"] as String?
    }

    fun getIsFemale(): String? {
        return data!!["isFemale"] as String?
    }

    fun getAddress(): String? {
        return data!!["address"] as String?
    }

    fun getBirthday(): String? {
        return data!!["birthday"] as String?
    }

    fun getGender(): String? {
        return data!!["gender"] as String?
    }

    fun getSchoolCode(): String? {
        return data!!["schoolCode"] as String?
    }

    fun getSchoolClass(): String? {
        return data!!["schoolClass"] as String?
    }

    fun getSchoolTel(): String? {
        return data!!["schoolTel"] as String?
    }

    fun getSchoolName(): String? {
        return data!!["schoolName"] as String?
    }

    fun getApplicantTel(): String? {
        return data!!["applicantTel"] as String?
    }

    fun getParentTel(): String? {
        return data!!["parentTel"] as String?
    }

    fun getQualificationExamPassedYear(): String? {
        return data!!["qualificationExamPassedYear"] as String?
    }

    fun getQualificationExamPassedMonth(): String? {
        return data!!["qualificationExamPassedMonth"] as String?
    }

    fun getGraduateYear(): String? {
        return data!!["graduateYear"] as String?
    }

    fun getGraduateMonth(): String? {
        return data!!["graduateMonth"] as String?
    }

    fun getProspectiveGraduateYear(): String? {
        return data!!["prospectiveGraduateYear"] as String?
    }

    fun getProspectiveGraduateMonth(): String? {
        return data!!["prospectiveGraduateMonth"] as String?
    }

    fun getIsQualificationExam(): String? {
        return data!!["isQualificationExam"] as String?
    }

    fun getIsGraduate(): String? {
        return data!!["isGraduate"] as String?
    }

    fun getIsProspectiveGraduate(): String? {
        return data!!["isProspectiveGraduate"] as String?
    }

    fun getIsDaejeon(): String? {
        return data!!["isDaejeon"] as String?
    }

    fun getIsNotDaejeon(): String? {
        return data!!["isNotDaejeon"] as String?
    }

    fun getIsBasicLiving(): String? {
        return data!!["isBasicLiving"] as String?
    }

    fun getIsFromNorth(): String? {
        return data!!["isFromNorth"] as String?
    }

    fun getIsLowestIncome(): String? {
        return data!!["isLowestIncome"] as String?
    }

    fun getIsMulticultural(): String? {
        return data!!["isMulticultural"] as String?
    }

    fun getIsOneParent(): String? {
        return data!!["isOneParent"] as String?
    }

    fun getIsTeenHouseholder(): String? {
        return data!!["isTeenHouseholder"] as String?
    }

    fun getIsPrivilegedAdmission(): String? {
        return data!!["isPrivilegedAdmission"] as String?
    }

    fun getIsNationalMerit(): String? {
        return data!!["isNationalMerit"] as String?
    }

    fun getIsCommon(): String? {
        return data!!["isCommon"] as String?
    }

    fun getIsMeister(): String? {
        return data!!["isMeister"] as String?
    }

    fun getIsSocialMerit(): String? {
        return data!!["isSocialMerit"] as String?
    }

    fun getConversionScore1st(): String? {
        return data!!["conversionScore1st"] as String?
    }

    fun getConversionScore2nd(): String? {
        return data!!["conversionScore2nd"] as String?
    }

    fun getConversionScore3rd(): String? {
        return data!!["conversionScore3rd"] as String?
    }

    fun getConversionScore(): String? {
        return data!!["conversionScore"] as String?
    }

    fun getAttendanceScore(): String? {
        return data!!["attendanceScore"] as String?
    }

    fun getVolunteerScore(): String? {
        return data!!["volunteerScore"] as String?
    }

    fun getFinalScore(): String? {
        return data!!["finalScore"] as String?
    }

    fun getYear(): String? {
        return data!!["year"] as String?
    }

    fun getMonth(): String? {
        return data!!["month"] as String?
    }

    fun getDay(): String? {
        return data!!["day"] as String?
    }

    fun getSelfIntroduction(): String? {
        return data!!["selfIntroduction"] as String?
    }

    fun getStudyPlan(): String? {
        return data!!["studyPlan"] as String?
    }

    fun getParentName(): String? {
        return data!!["parentName"] as String?
    }

    fun getIsDaejeonAndMeister(): String? {
        return data!!["isDaejeonAndMeister"] as String?
    }

    fun getIsDaejeonAndSocialMerit(): String? {
        return data!!["isDaejeonAndSocialMerit"] as String?
    }

    fun getIsNotDaejeonAndMeister(): String? {
        return data!!["isNotDaejeonAndMeister"] as String?
    }

    fun getIsNotDaejeonAndSocialMerit(): String? {
        return data!!["isNotDaejeonAndSocialMerit"] as String?
    }
}