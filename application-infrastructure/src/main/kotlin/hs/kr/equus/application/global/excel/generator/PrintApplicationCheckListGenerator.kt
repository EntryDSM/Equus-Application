package hs.kr.equus.application.global.excel.generator

import hs.kr.equus.application.domain.application.spi.PrintApplicationCheckListPort
import hs.kr.equus.application.domain.application.usecase.dto.vo.ApplicationCheckListVO
import hs.kr.equus.application.global.excel.exception.ExcelExceptions
import hs.kr.equus.application.global.excel.model.ApplicationCheckList
import org.apache.poi.ss.usermodel.Row
import org.springframework.stereotype.Component
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.servlet.http.HttpServletResponse

@Component
class PrintApplicationCheckListGenerator : PrintApplicationCheckListPort {
    override fun execute(
        httpServletResponse: HttpServletResponse,
        applicationCheckListVO: List<ApplicationCheckListVO>
    ) {
        val applicationCheckList = ApplicationCheckList()
        val sheet = applicationCheckList.getSheet()
        applicationCheckList.format()
        applicationCheckListVO.forEachIndexed { index, it ->
            val row = sheet.createRow(index + 1)
            insertCode(row, it)
        }

        try {
            httpServletResponse.contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            val formatFilename = "attachment;filename=\"지원자점검표"
            val time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy년MM월dd일_HH시mm분"))
            val fileName = String(("$formatFilename$time.xlsx\"").toByteArray(Charsets.UTF_8), Charsets.ISO_8859_1)
            httpServletResponse.setHeader("Content-Disposition", fileName)

            applicationCheckList.getWorkbook().write(httpServletResponse.outputStream)
        } catch (e: IOException) {
            throw ExcelExceptions.ExcelIOException()
        }
    }
    private fun insertCode(row: Row, applicationCheckListVO: ApplicationCheckListVO) {
        row.createCell(0).setCellValue(applicationCheckListVO.receiptCode.toString())
        row.createCell(1).setCellValue(applicationCheckListVO.applicationType.name)
        row.createCell(2).setCellValue(applicationCheckListVO.isDaejeon.toString())
        row.createCell(3).setCellValue(applicationCheckListVO.applicationRemark.name)
        row.createCell(4).setCellValue(applicationCheckListVO.applicationName)
        row.createCell(5).setCellValue(applicationCheckListVO.birthDate.toString())
        row.createCell(6).setCellValue(applicationCheckListVO.address)
        row.createCell(7).setCellValue(applicationCheckListVO.applicantTel)
        row.createCell(8).setCellValue(applicationCheckListVO.sex.name)
        row.createCell(9).setCellValue(applicationCheckListVO.educationalStatus.name)
        row.createCell(10).setCellValue(applicationCheckListVO.graduationYear.toString())
        row.createCell(11).setCellValue(applicationCheckListVO.school)
        row.createCell(12).setCellValue(applicationCheckListVO.classNumber)
        row.createCell(13).setCellValue(applicationCheckListVO.parentName)
        row.createCell(14).setCellValue(applicationCheckListVO.parentTel)

        row.createCell(15).setCellValue(applicationCheckListVO.koreanGrade.getOrElse(3) { 'X' }.toString())
        row.createCell(16).setCellValue(applicationCheckListVO.socialGrade.getOrElse(3) { 'X' }.toString())
        row.createCell(17).setCellValue(applicationCheckListVO.historyGrade.getOrElse(3) { 'X' }.toString())
        row.createCell(18).setCellValue(applicationCheckListVO.mathGrade.getOrElse(3) { 'X' }.toString())
        row.createCell(19).setCellValue(applicationCheckListVO.scienceGrade.getOrElse(3) { 'X' }.toString())
        row.createCell(20).setCellValue(applicationCheckListVO.techAndHomeGrade.getOrElse(3) { 'X' }.toString())
        row.createCell(21).setCellValue(applicationCheckListVO.englishGrade.getOrElse(3) { 'X' }.toString())

        row.createCell(22).setCellValue(applicationCheckListVO.koreanGrade.getOrElse(2) { 'X' }.toString())
        row.createCell(23).setCellValue(applicationCheckListVO.socialGrade.getOrElse(2) { 'X' }.toString())
        row.createCell(24).setCellValue(applicationCheckListVO.historyGrade.getOrElse(2) { 'X' }.toString())
        row.createCell(25).setCellValue(applicationCheckListVO.mathGrade.getOrElse(2) { 'X' }.toString())
        row.createCell(26).setCellValue(applicationCheckListVO.scienceGrade.getOrElse(2) { 'X' }.toString())
        row.createCell(27).setCellValue(applicationCheckListVO.techAndHomeGrade.getOrElse(2) { 'X' }.toString())
        row.createCell(28).setCellValue(applicationCheckListVO.englishGrade.getOrElse(2) { 'X' }.toString())

        row.createCell(29).setCellValue(applicationCheckListVO.koreanGrade.getOrElse(1) { 'X' }.toString())
        row.createCell(30).setCellValue(applicationCheckListVO.socialGrade.getOrElse(1) { 'X' }.toString())
        row.createCell(31).setCellValue(applicationCheckListVO.historyGrade.getOrElse(1) { 'X' }.toString())
        row.createCell(32).setCellValue(applicationCheckListVO.mathGrade.getOrElse(1) { 'X' }.toString())
        row.createCell(33).setCellValue(applicationCheckListVO.scienceGrade.getOrElse(1) { 'X' }.toString())
        row.createCell(34).setCellValue(applicationCheckListVO.techAndHomeGrade.getOrElse(1) { 'X' }.toString())
        row.createCell(35).setCellValue(applicationCheckListVO.englishGrade.getOrElse(1) { 'X' }.toString())

        row.createCell(36).setCellValue(applicationCheckListVO.koreanGrade.getOrElse(0) { 'X' }.toString())
        row.createCell(37).setCellValue(applicationCheckListVO.socialGrade.getOrElse(0) { 'X' }.toString())
        row.createCell(38).setCellValue(applicationCheckListVO.historyGrade.getOrElse(0) { 'X' }.toString())
        row.createCell(39).setCellValue(applicationCheckListVO.mathGrade.getOrElse(0) { 'X' }.toString())
        row.createCell(40).setCellValue(applicationCheckListVO.scienceGrade.getOrElse(0) { 'X' }.toString())
        row.createCell(41).setCellValue(applicationCheckListVO.techAndHomeGrade.getOrElse(0) { 'X' }.toString())
        row.createCell(42).setCellValue(applicationCheckListVO.englishGrade.getOrElse(0) { 'X' }.toString())

        row.createCell(43).setCellValue("총합 점수: " + applicationCheckListVO.totalScore3rdYear) // Example placeholder
        row.createCell(44).setCellValue("직전 학기 성적 총합: " + applicationCheckListVO.lastSemesterTotal)
        row.createCell(45).setCellValue("직전전 학기 성적 총합: " + applicationCheckListVO.beforeLastSemesterTotal)
        row.createCell(46).setCellValue(applicationCheckListVO.convertedScore.toString())
        row.createCell(47).setCellValue(applicationCheckListVO.volunteerHours.toString())
        row.createCell(48).setCellValue(applicationCheckListVO.volunteerScore.toString())
        row.createCell(49).setCellValue(applicationCheckListVO.absenceDayCount.toString())
        row.createCell(50).setCellValue(applicationCheckListVO.latenessCount.toString())
        row.createCell(51).setCellValue(applicationCheckListVO.earlyLeaveCount.toString())
        row.createCell(52).setCellValue(applicationCheckListVO.result)
        row.createCell(53).setCellValue(applicationCheckListVO.attendanceScore.toString())
        row.createCell(54).setCellValue(applicationCheckListVO.competition.toString())
        row.createCell(55).setCellValue(applicationCheckListVO.certification.toString())
        row.createCell(56).setCellValue(applicationCheckListVO.extraScoreItem.toString())
        row.createCell(57).setCellValue(applicationCheckListVO.totalScore1stSelection.toString())
    }

}