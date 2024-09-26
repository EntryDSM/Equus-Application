package hs.kr.equus.application.global.excel.generator

import hs.kr.equus.application.domain.application.spi.PrintApplicationInfoPort
import hs.kr.equus.application.domain.application.usecase.dto.vo.ApplicationInfoVO
import hs.kr.equus.application.domain.application.service.ApplicationService
import hs.kr.equus.application.domain.applicationCase.model.GraduationCase
import hs.kr.equus.application.domain.graduationInfo.model.Graduation
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
        row.createCell(10).setCellValue(applicationService.safeGetValue(applicationInfoVO.graduationInfo?.graduateDate))
        row.createCell(11).setCellValue(applicationService.safeGetValue(applicationInfoVO.school?.name))
        val graduation = applicationInfoVO.graduationInfo as? Graduation
        row.createCell(12).setCellValue(applicationService.safeGetValue(graduation?.studentNumber?.classNumber))
        row.createCell(13).setCellValue(applicationService.safeGetValue(applicationInfoVO.application.parentName))
        row.createCell(14).setCellValue(applicationService.safeGetValue(applicationInfoVO.application.parentTel))
        val graduationCase = applicationInfoVO.applicationCase as? GraduationCase
        val subject = graduationCase?.gradesPerSubject()
        row.createCell(15).setCellValue(applicationService.safeGetValue(subject?.get("국어")?.get(0)))
        row.createCell(16).setCellValue(applicationService.safeGetValue(subject?.get("사회")?.get(0)))
        row.createCell(17).setCellValue(applicationService.safeGetValue(subject?.get("역사")?.get(0)))
        row.createCell(18).setCellValue(applicationService.safeGetValue(subject?.get("수학")?.get(0)))
        row.createCell(19).setCellValue(applicationService.safeGetValue(subject?.get("과학")?.get(0)))
        row.createCell(20).setCellValue(applicationService.safeGetValue(subject?.get("기술가정")?.get(0)))
        row.createCell(21).setCellValue(applicationService.safeGetValue(subject?.get("영어")?.get(0)))
        row.createCell(22).setCellValue(applicationService.safeGetValue(subject?.get("국어")?.get(1)))
        row.createCell(23).setCellValue(applicationService.safeGetValue(subject?.get("사회")?.get(1)))
        row.createCell(24).setCellValue(applicationService.safeGetValue(subject?.get("역사")?.get(1)))
        row.createCell(25).setCellValue(applicationService.safeGetValue(subject?.get("수학")?.get(1)))
        row.createCell(26).setCellValue(applicationService.safeGetValue(subject?.get("과학")?.get(1)))
        row.createCell(27).setCellValue(applicationService.safeGetValue(subject?.get("기술가정")?.get(1)))
        row.createCell(28).setCellValue(applicationService.safeGetValue(subject?.get("영어")?.get(1)))
        row.createCell(29).setCellValue(applicationService.safeGetValue(subject?.get("국어")?.get(2)))
        row.createCell(30).setCellValue(applicationService.safeGetValue(subject?.get("사회")?.get(2)))
        row.createCell(31).setCellValue(applicationService.safeGetValue(subject?.get("역사")?.get(2)))
        row.createCell(32).setCellValue(applicationService.safeGetValue(subject?.get("수학")?.get(2)))
        row.createCell(33).setCellValue(applicationService.safeGetValue(subject?.get("과학")?.get(2)))
        row.createCell(34).setCellValue(applicationService.safeGetValue(subject?.get("기술가정")?.get(2)))
        row.createCell(35).setCellValue(applicationService.safeGetValue(subject?.get("영어")?.get(2)))
        row.createCell(36).setCellValue(applicationService.safeGetValue(subject?.get("국어")?.get(3)))
        row.createCell(37).setCellValue(applicationService.safeGetValue(subject?.get("사회")?.get(3)))
        row.createCell(38).setCellValue(applicationService.safeGetValue(subject?.get("역사")?.get(3)))
        row.createCell(39).setCellValue(applicationService.safeGetValue(subject?.get("수학")?.get(3)))
        row.createCell(40).setCellValue(applicationService.safeGetValue(subject?.get("과학")?.get(3)))
        row.createCell(41).setCellValue(applicationService.safeGetValue(subject?.get("기술가정")?.get(3)))
        row.createCell(42).setCellValue(applicationService.safeGetValue(subject?.get("영어")?.get(3)))
        row.createCell(43).setCellValue(applicationService.safeGetValue(applicationInfoVO.score?.thirdGradeScore))
        row.createCell(44).setCellValue(applicationService.safeGetValue(applicationInfoVO.score?.thirdBeforeScore))
        row.createCell(45).setCellValue(applicationService.safeGetValue(applicationInfoVO.score?.thirdBeforeBeforeScore))
        row.createCell(46).setCellValue(applicationService.safeGetValue(applicationInfoVO.score?.totalGradeScore))
        row.createCell(47).setCellValue(applicationService.safeGetValue(graduationCase?.volunteerTime))
        row.createCell(48).setCellValue(applicationService.safeGetValue(applicationInfoVO.score?.volunteerScore))
        row.createCell(49).setCellValue(applicationService.safeGetValue(graduationCase?.absenceDayCount))
        row.createCell(50).setCellValue(applicationService.safeGetValue(graduationCase?.latenessCount))
        row.createCell(51).setCellValue(applicationService.safeGetValue(graduationCase?.earlyLeaveCount))
        row.createCell(52).setCellValue(applicationService.safeGetValue(graduationCase?.lectureAbsenceCount))
        row.createCell(53).setCellValue(applicationService.safeGetValue(applicationInfoVO.score?.attendanceScore))
        row.createCell(54).setCellValue(applicationService.translateBoolean(applicationInfoVO.applicationCase?.extraScoreItem?.hasCompetitionPrize))
        row.createCell(55).setCellValue(applicationService.translateBoolean(applicationInfoVO.applicationCase?.extraScoreItem?.hasCertificate))
        row.createCell(56).setCellValue(applicationService.safeGetDouble(applicationInfoVO.score?.extraScore).toString())
        row.createCell(57).setCellValue(applicationService.safeGetDouble(applicationInfoVO.score?.totalScore).toString())
    }
}
