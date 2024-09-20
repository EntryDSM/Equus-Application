package hs.kr.equus.application.global.excel.generator

import hs.kr.equus.application.domain.application.service.ApplicationService
import hs.kr.equus.application.domain.application.spi.PrintApplicationCheckListPort
import hs.kr.equus.application.domain.application.usecase.dto.vo.ApplicationInfoVO
import hs.kr.equus.application.global.excel.exception.ExcelExceptions
import org.apache.poi.ss.usermodel.*
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.ss.util.RegionUtil
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.stereotype.Component
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.servlet.ServletOutputStream
import javax.servlet.http.HttpServletResponse

@Component
class PrintApplicationCheckListGenerator(
    private val applicationService: ApplicationService
) : PrintApplicationCheckListPort {

    private val workbook: Workbook = XSSFWorkbook()
    private val sheet: Sheet = workbook.createSheet("application Check List")

    override fun printApplicationCheckList(
        applicationInfoVO: List<ApplicationInfoVO>,
        httpServletResponse: HttpServletResponse
    ) {
        var outputStream: ServletOutputStream? = null
        var dh = 0
        try {
            applicationInfoVO.forEach { it ->
                    formatSheet(dh)
                    insertDataIntoSheet(it, dh)
                    dh += 20
                }
                println(dh)

            httpServletResponse.apply {
                contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                val formatFilename = "attachment;filename=\"점검표"
                val time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy년MM월dd일_HH시mm분"))
                val fileName = String(("$formatFilename$time.xlsx\"").toByteArray(Charsets.UTF_8), Charsets.ISO_8859_1)
                httpServletResponse.setHeader("Content-Disposition", fileName)
            }

            outputStream = httpServletResponse.outputStream
            workbook.write(outputStream)
            outputStream.flush()
        } catch (e: IOException) {
            if (!httpServletResponse.isCommitted) {
                httpServletResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR)
            } else {
                throw ExcelExceptions.ExcelIOException()
            }
        } finally {
            try {
                outputStream?.close()
            } catch (e: Exception) {
                workbook.close()
            }
        }
    }

    private fun formatSheet(dh: Int) {
        sheet.apply {
            mergeRegions(dh)
            applyBorderStyles(dh)
            setCellValues(dh)
        }
    }

    private fun Sheet.mergeRegions(rowOffset: Int) {
        val mergedRegions = arrayOf(
            CellRangeAddress(1 + rowOffset, 1 + rowOffset, 3, 5),
            CellRangeAddress(3 + rowOffset, 3 + rowOffset, 2, 3),
            CellRangeAddress(4 + rowOffset, 4 + rowOffset, 2, 3),
            CellRangeAddress(5 + rowOffset, 5 + rowOffset, 2, 3),
            CellRangeAddress(4 + rowOffset, 4 + rowOffset, 6, 7),
            CellRangeAddress(5 + rowOffset, 5 + rowOffset, 6, 7),
            CellRangeAddress(3 + rowOffset, 3 + rowOffset, 6, 7)
        )
        mergedRegions.forEach {
            if (!isRegionMerged(it)) {
                addMergedRegion(it)
            }
        }
    }

    private fun Sheet.isRegionMerged(region: CellRangeAddress): Boolean {
        return mergedRegions.any { it.firstRow == region.firstRow && it.lastRow == region.lastRow && it.firstColumn == region.firstColumn && it.lastColumn == region.lastColumn }
    }

    private fun Sheet.applyBorderStyles(dh: Int) {
        val borderRegionsDashedBottom = arrayOf(
            intArrayOf(3 + dh, 3 + dh, 1, 1),
            intArrayOf(4 + dh, 4 + dh, 1, 3),
            intArrayOf(5 + dh, 5 + dh, 1, 3),
            intArrayOf(3 + dh, 3 + dh, 5, 7),
            intArrayOf(4 + dh, 4 + dh, 5, 7),
            intArrayOf(5 + dh, 5 + dh, 5, 7),
            intArrayOf(7 + dh, 7 + dh, 1, 7),
            intArrayOf(11 + dh, 11 + dh, 1, 7),
            intArrayOf(12 + dh, 12 + dh, 1, 7),
            intArrayOf(13 + dh, 13 + dh, 1, 7),
            intArrayOf(14 + dh, 14 + dh, 1, 7),
            intArrayOf(15 + dh, 15 + dh, 1, 7),
            intArrayOf(16 + dh, 16 + dh, 1, 7),
        )
        setBorderStyle(borderRegionsDashedBottom, BorderStyle.DASHED, Direction.BOTTOM)

        val borderRegionsThin = arrayOf(
            intArrayOf(1 + dh, 1 + dh, 1, 7),
            intArrayOf(3 + dh, 3 + dh, 1, 1),
            intArrayOf(4 + dh, 5 + dh, 1, 3),
            intArrayOf(3 + dh, 5 + dh, 5, 7),
            intArrayOf(7 + dh, 8 + dh, 1, 7),
            intArrayOf(10 + dh, 17 + dh, 1, 7),
            intArrayOf(10 + dh, 10 + dh, 1, 5),
            intArrayOf(18 + dh, 18 + dh, 1, 5),
        )
        setBorderStyle(borderRegionsThin, BorderStyle.THIN, Direction.ALL)

        val borderRegionsThick = arrayOf(
            intArrayOf(18 + dh, 18 + dh, 6, 7),
            intArrayOf(10 + dh, 10 + dh, 6, 7),
            intArrayOf(1 + dh, 1 + dh, 2, 2),
            intArrayOf(3 + dh, 3 + dh, 2, 2),
            intArrayOf(18 + dh, 18 + dh, 6, 7),
            intArrayOf(19 + dh, 19 + dh, 6, 7)
        )
        setBorderStyle(borderRegionsThick, BorderStyle.THICK, Direction.ALL)

        val borderRegionsDashedRight = arrayOf(
            intArrayOf(18 + dh, 18 + dh, 2, 3),
            intArrayOf(1 + dh, 1 + dh, 4, 5),
            intArrayOf(1 + dh, 1 + dh, 5, 6),
            intArrayOf(4 + dh, 5 + dh, 1, 2),
            intArrayOf(7 + dh, 8 + dh, 1, 1),
            intArrayOf(7 + dh, 8 + dh, 2, 2),
            intArrayOf(7 + dh, 8 + dh, 3, 3),
            intArrayOf(7 + dh, 8 + dh, 4, 4),
            intArrayOf(7 + dh, 8 + dh, 5, 5),
            intArrayOf(7 + dh, 8 + dh, 6, 6),
            intArrayOf(10 + dh, 18 + dh, 1, 1),
            intArrayOf(10 + dh, 18 + dh, 2, 2),
            intArrayOf(10 + dh, 18 + dh, 3, 3),
            intArrayOf(10 + dh, 18 + dh, 4, 4),
            intArrayOf(10 + dh, 18 + dh, 6, 6),
            intArrayOf(3 + dh, 5 + dh, 1, 1),
            intArrayOf(19 + dh, 19 + dh, 6, 6)
        )
        setBorderStyle(borderRegionsDashedRight, BorderStyle.DASHED, Direction.RIGHT)

        val borderRegionsThinRight = arrayOf(
            intArrayOf(11 + dh, 17 + dh, 5, 5),
            intArrayOf(3 + dh, 5 + dh, 5, 5)
        )
        setBorderStyle(borderRegionsThinRight, BorderStyle.THIN, Direction.RIGHT)
    }

    private fun Sheet.setCellValues(dh: Int) {
        val cellValues = mapOf(
            Pair(1 + dh, 1) to "접수번호",
            Pair(3 + dh, 5) to "학번",
            Pair(4 + dh, 5) to "학생",
            Pair(5 + dh, 5) to "보호자",
            Pair(7 + dh, 1) to "결석",
            Pair(7 + dh, 2) to "지각",
            Pair(7 + dh, 3) to "조퇴",
            Pair(7 + dh, 4) to "결과",
            Pair(7 + dh, 5) to "출석점수",
            Pair(7 + dh, 6) to "봉사시간",
            Pair(7 + dh, 7) to "봉사점수",
            Pair(10 + dh, 1) to "과목",
            Pair(10 + dh, 2) to "3_2학기",
            Pair(10 + dh, 3) to "3_1학기",
            Pair(10 + dh, 4) to "직전",
            Pair(10 + dh, 5) to "직전전",
            Pair(10 + dh, 6) to "교과성적",
            Pair(11 + dh, 6) to "대회",
            Pair(12 + dh, 6) to "기능사",
            Pair(13 + dh, 6) to "가산점",
            Pair(19 + dh, 6) to "총점",
            Pair(18 + dh, 6) to "환산점수",
            Pair(11 + dh, 1) to "국어",
            Pair(12 + dh, 1) to "사회",
            Pair(13 + dh, 1) to "역사",
            Pair(14 + dh, 1) to "수학",
            Pair(15 + dh, 1) to "과학",
            Pair(16 + dh, 1) to "기술가정",
            Pair(17 + dh, 1) to "영어",
            Pair(18 + dh, 1) to "점수"
        )
        cellValues.forEach { (cell, value) ->
            getCell(cell.first, cell.second).setCellValue(value)
        }
    }


    private fun setBorderStyle(regions: Array<IntArray>, borderStyle: BorderStyle, direction: Direction) {
        regions.forEach { region ->
            val address = CellRangeAddress(region[0], region[1], region[2], region[3])
            when (direction) {
                Direction.TOP -> RegionUtil.setBorderTop(borderStyle, address, sheet)
                Direction.BOTTOM -> RegionUtil.setBorderBottom(borderStyle, address, sheet)
                Direction.LEFT -> RegionUtil.setBorderLeft(borderStyle, address, sheet)
                Direction.RIGHT -> RegionUtil.setBorderRight(borderStyle, address, sheet)
                Direction.ALL -> {
                    RegionUtil.setBorderTop(borderStyle, address, sheet)
                    RegionUtil.setBorderBottom(borderStyle, address, sheet)
                    RegionUtil.setBorderLeft(borderStyle, address, sheet)
                    RegionUtil.setBorderRight(borderStyle, address, sheet)
                }
            }
        }
    }

    private fun getCell(rowNum: Int, cellNum: Int): Cell {
        val row = sheet.getRow(rowNum) ?: sheet.createRow(rowNum)
        return row.getCell(cellNum) ?: row.createCell(cellNum)
    }

    private fun setRowHeight(rowIndex: Int, height: Int) {
        val row = sheet.getRow(rowIndex) ?: sheet.createRow(rowIndex)
        row.heightInPoints = height.toFloat()
    }


    private fun insertDataIntoSheet(applicationInfoVO: ApplicationInfoVO, dh: Int) {
        val number = applicationInfoVO.graduation?.studentNumber
        val studentNumber = if (number != null) {
            number.gradeNumber.toInt() * 10000 +
                    number.classNumber.toInt() * 100 +
                    number.studentNumber.toInt()
        } else {
            null
        }
        getCell(dh + 1, 2).setCellValue(applicationInfoVO.application.receiptCode.toString())
        getCell(dh + 1, 3).setCellValue(applicationService.safeGetValue(applicationInfoVO.school?.name))
        getCell(dh + 1, 6).setCellValue(applicationService.translateEducationalStatus(applicationInfoVO.application.educationalStatus))
        getCell(dh + 1, 7).setCellValue(applicationService.safeGetValue(applicationInfoVO.graduation?.graduateDate?.year))
        getCell(dh + 4, 1).setCellValue(applicationService.translateApplicationType(applicationInfoVO.application.applicationType))
        getCell(dh + 3, 2).setCellValue(applicationInfoVO.application.applicantName)
        getCell(dh + 3, 6).setCellValue(applicationService.safeGetValue(studentNumber))
        getCell(dh + 3, 1).setCellValue(applicationService.translateIsDaejeon(applicationInfoVO.application.isDaejeon))
        getCell(dh + 4, 2).setCellValue(applicationInfoVO.application.birthDate.toString().replace("-", "."))
        getCell(dh + 4, 6).setCellValue(applicationService.formatPhoneNumber(applicationInfoVO.application.applicantTel))
        getCell(dh + 5, 1).setCellValue(applicationService.translateApplicationRemark(applicationInfoVO.application.applicationRemark))
        getCell(dh + 5, 2).setCellValue(applicationService.translateSex(applicationInfoVO.application.sex))
        getCell(dh + 5, 6).setCellValue(applicationService.safeGetValue(applicationService.formatPhoneNumber(applicationInfoVO.application.parentTel)))

        getCell(dh + 8, 1).setCellValue(applicationService.safeGetDouble(applicationInfoVO.graduationCase?.absenceDayCount).toString())
        getCell(dh + 8, 2).setCellValue(applicationService.safeGetDouble(applicationInfoVO.graduationCase?.latenessCount).toString())
        getCell(dh + 8, 3).setCellValue(applicationService.safeGetDouble(applicationInfoVO.graduationCase?.earlyLeaveCount).toString())
        getCell(dh + 8, 4).setCellValue(applicationService.safeGetDouble(applicationInfoVO.graduationCase?.lectureAbsenceCount).toString())
        getCell(dh + 8, 5).setCellValue(applicationService.safeGetDouble(applicationInfoVO.score?.attendanceScore).toString())
        getCell(dh + 8, 6).setCellValue(applicationService.safeGetDouble(applicationInfoVO.graduationCase?.volunteerTime).toString())
        getCell(dh + 8, 7).setCellValue(applicationService.safeGetDouble(applicationInfoVO.score?.volunteerScore).toString())
        getCell(dh + 10, 7).setCellValue(applicationService.safeGetDouble(applicationInfoVO.score?.calculateSubjectScore()).toString())

        val subjectGrades = applicationInfoVO.graduationCase?.gradesPerSubject()
        var rowIndex = dh + 11
        subjectGrades?.forEach { (subject, grades) ->
            getCell(rowIndex, 1).setCellValue(applicationService.safeGetValue(subject))
            grades.forEachIndexed { index, grade ->
                getCell(rowIndex, index + 2).setCellValue(grade)
            }
            rowIndex++
        }

        getCell(dh + 11, 7).setCellValue(applicationService.translateBoolean(applicationInfoVO.graduationCase?.extraScoreItem?.hasCompetitionPrize))
        getCell(dh + 12, 7).setCellValue(applicationService.translateBoolean(applicationInfoVO.graduationCase?.extraScoreItem?.hasCertificate))
        getCell(dh + 13, 7).setCellValue(applicationService.safeGetDouble(applicationInfoVO.score?.extraScore).toString())
        getCell(dh + 18, 2).setCellValue(applicationService.safeGetDouble(applicationInfoVO.score?.thirdScore).toString())
        getCell(dh + 18, 3).setCellValue(applicationService.safeGetDouble(applicationInfoVO.score?.thirdGradeScore).toString())
        getCell(dh + 18, 4).setCellValue(applicationService.safeGetDouble(applicationInfoVO.score?.thirdBeforeScore).toString())
        getCell(dh + 18, 5).setCellValue(applicationService.safeGetDouble(applicationInfoVO.score?.thirdBeforeBeforeScore).toString())
        getCell(dh + 18, 7).setCellValue(applicationService.safeGetDouble(applicationInfoVO.score?.totalGradeScore).toString())
        getCell(dh + 19,  7).setCellValue(applicationService.safeGetDouble(applicationInfoVO.score?.totalScore).toString())

        setRowHeight(dh + 2, 10)
        setRowHeight(dh + 6, 10)
        setRowHeight(dh + 9, 10)
        setRowHeight(dh + 0, 71)
    }


    enum class Direction {
        TOP,
        BOTTOM,
        LEFT,
        RIGHT,
        ALL
    }
}
