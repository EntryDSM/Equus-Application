package hs.kr.equus.application.global.excel.generator

import hs.kr.equus.application.domain.application.spi.PrintApplicationInfoPort
import hs.kr.equus.application.domain.application.usecase.dto.vo.ApplicationInfoVO
import hs.kr.equus.application.domain.application.service.ApplicationService
import hs.kr.equus.application.global.excel.exception.ExcelExceptions
import hs.kr.equus.application.global.excel.model.ApplicationInfo
import org.apache.poi.ss.usermodel.Row
import org.springframework.stereotype.Component
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.servlet.http.HttpServletResponse

@Component
class PrintApplicationInfoGenerator(
    private val applicationService: ApplicationService
) : PrintApplicationInfoPort {
    override fun execute(
        httpServletResponse: HttpServletResponse,
        applicationInfoVO: List<ApplicationInfoVO>
    ) {
        val applicationInfo = ApplicationInfo()
        val sheet = applicationInfo.getSheet()
        applicationInfo.format()
        applicationInfoVO.forEachIndexed { index, it ->
            val row = sheet.createRow(index + 1)
            insertCode(row, it)
        }

        try {
            httpServletResponse.contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            val formatFilename = "attachment;filename=\"전형자료"
            val time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy년MM월dd일_HH시mm분"))
            val fileName = String(("$formatFilename$time.xlsx\"").toByteArray(Charsets.UTF_8), Charsets.ISO_8859_1)
            httpServletResponse.setHeader("Content-Disposition", fileName)

            applicationInfo.getWorkbook().use { workbook ->
                workbook.write(httpServletResponse.outputStream)
            }
        } catch (e: IOException) {
            throw ExcelExceptions.ExcelIOException().initCause(e)
        }
    }
    private fun insertCode(row: Row, applicationInfoVO: ApplicationInfoVO) {

        row.createCell(0).setCellValue(applicationService.safeGetValue(applicationInfoVO.application.receiptCode))
        row.createCell(1).setCellValue(applicationService.translateApplicationType(applicationInfoVO.application.applicationType))
        row.createCell(2).setCellValue(applicationService.translateIsDaejeon(applicationInfoVO.application.isDaejeon))
        row.createCell(3).setCellValue(applicationService.translateApplicationRemark(applicationInfoVO.application.applicationRemark))
        row.createCell(4).setCellValue(applicationService.safeGetValue(applicationInfoVO.application.applicantName))
        row.createCell(5).setCellValue(applicationService.safeGetValue(applicationInfoVO.application.birthDate))
        row.createCell(6).setCellValue(applicationService.safeGetValue(applicationInfoVO.application.detailAddress))
        row.createCell(7).setCellValue(applicationService.safeGetValue(applicationInfoVO.application.applicantTel))
        row.createCell(8).setCellValue(applicationService.translateSex(applicationInfoVO.application.sex))
        row.createCell(9).setCellValue(applicationService.translateEducationalStatus(applicationInfoVO.application.educationalStatus))
        row.createCell(10).setCellValue(applicationService.safeGetValue(applicationInfoVO.graduation?.graduateDate))
        row.createCell(11).setCellValue(applicationService.safeGetValue(applicationInfoVO.graduation?.schoolCode))
        row.createCell(12).setCellValue(applicationService.safeGetValue(applicationInfoVO.graduation?.studentNumber?.classNumber))
        row.createCell(13).setCellValue(applicationService.safeGetValue(applicationInfoVO.application.parentName))
        row.createCell(14).setCellValue(applicationService.safeGetValue(applicationInfoVO.application.parentTel))

        val grades = listOf(
            applicationInfoVO.graduationCase?.koreanGrade,
            applicationInfoVO.graduationCase?.socialGrade,
            applicationInfoVO.graduationCase?.historyGrade,
            applicationInfoVO.graduationCase?.mathGrade,
            applicationInfoVO.graduationCase?.scienceGrade,
            applicationInfoVO.graduationCase?.techAndHomeGrade,
            applicationInfoVO.graduationCase?.englishGrade
        )

        grades.forEachIndexed { i, grade ->
            row.createCell(15 + i).setCellValue(applicationService.safeGetValue(grade?.getOrNull(3)))
        }

        grades.forEachIndexed { i, grade ->
            row.createCell(22 + i).setCellValue(applicationService.safeGetValue(grade?.getOrNull(2)))
        }

        grades.forEachIndexed { i, grade ->
            row.createCell(29 + i).setCellValue(applicationService.safeGetValue(grade?.getOrNull(1)))
        }

        grades.forEachIndexed { i, grade ->
            row.createCell(36 + i).setCellValue(applicationService.safeGetValue(grade?.getOrNull(0)))
        }

        row.createCell(43).setCellValue(applicationService.safeGetValue(applicationInfoVO.score?.thirdGradeScore))
        row.createCell(44).setCellValue(applicationService.safeGetValue(applicationInfoVO.score?.thirdBeforeScore))
        row.createCell(45).setCellValue(applicationService.safeGetValue(applicationInfoVO.score?.thirdBeforeBeforeScore))
        row.createCell(46).setCellValue(applicationService.safeGetValue(applicationInfoVO.graduationCase?.calculateGradeScores()))
        row.createCell(47).setCellValue(applicationService.safeGetValue(applicationInfoVO.graduationCase?.volunteerTime))
        row.createCell(48).setCellValue(applicationService.safeGetValue(applicationInfoVO.graduationCase?.calculateVolunteerScore()))
        row.createCell(49).setCellValue(applicationService.safeGetValue(applicationInfoVO.graduationCase?.absenceDayCount))
        row.createCell(50).setCellValue(applicationService.safeGetValue(applicationInfoVO.graduationCase?.latenessCount))
        row.createCell(51).setCellValue(applicationService.safeGetValue(applicationInfoVO.graduationCase?.earlyLeaveCount))
        row.createCell(52).setCellValue(applicationService.safeGetValue(applicationInfoVO.graduationCase?.lectureAbsenceCount))
        row.createCell(53).setCellValue(applicationService.safeGetValue(applicationInfoVO.graduationCase?.calculateAttendanceScore()))
        row.createCell(54).setCellValue(applicationService.safeGetValue(applicationInfoVO.graduationCase?.extraScoreItem?.hasCompetitionPrize))
        row.createCell(55).setCellValue(applicationService.safeGetValue(applicationInfoVO.graduationCase?.extraScoreItem?.hasCertificate))
        row.createCell(56).setCellValue(applicationService.safeGetValue(applicationInfoVO.graduationCase?.calculateAdditionalScore(applicationInfoVO.application.isCommon())))
        row.createCell(57).setCellValue(applicationService.safeGetValue(applicationInfoVO.score?.totalScore))
    }
}