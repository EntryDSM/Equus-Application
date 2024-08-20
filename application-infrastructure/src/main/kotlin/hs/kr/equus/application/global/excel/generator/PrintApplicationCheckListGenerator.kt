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
import javax.servlet.http.HttpServletResponse

@Component
class PrintApplicationCheckListGenerator(
    private val applicationService: ApplicationService
) : PrintApplicationCheckListPort {

    private val workbook: Workbook = XSSFWorkbook()
    private val sheet: Sheet = workbook.createSheet("Application Check List")

    override fun printApplicationCheckList(
        applicationInfoVO: List<ApplicationInfoVO>,
        httpServletResponse: HttpServletResponse
    ) {
        formatSheet()

        applicationInfoVO.forEach { insertDataIntoSheet(it) }

        try {
            httpServletResponse.apply {
                contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                val formatFilename = "attachment;filename=\"점검표"
                val time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy년MM월dd일_HH시mm분"))
                val fileName = String(("$formatFilename$time.xlsx\"").toByteArray(Charsets.UTF_8), Charsets.ISO_8859_1)
                httpServletResponse.setHeader("Content-Disposition", fileName)
            }

            workbook.use { wb ->
                wb.write(httpServletResponse.outputStream)
            }
        } catch (e: IOException) {
            throw ExcelExceptions.ExcelIOException().initCause(e)
        }
    }

    private fun formatSheet() {
        sheet.apply {
            setColumnWidths()
            mergeRegions()
            applyBorderStyles()
            setCellValues()
        }
    }

    private fun Sheet.setColumnWidths() {
        setColumnWidth(0, 250)
        setColumnWidth(8, 250)
    }

    private fun Sheet.mergeRegions() {
        val mergedRegions = arrayOf(
            CellRangeAddress(1, 1, 3, 5),
            CellRangeAddress(18, 18, 2, 3),
            CellRangeAddress(3, 3, 2, 3),
            CellRangeAddress(4, 4, 2, 3),
            CellRangeAddress(5, 5, 2, 3),
            CellRangeAddress(4, 4, 6, 7),
            CellRangeAddress(5, 5, 6, 7),
            CellRangeAddress(3, 3, 6, 7)
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

    private fun Sheet.applyBorderStyles() {
        val borderRegionsDashedBottom = arrayOf(
            intArrayOf(3, 3, 1, 1),
            intArrayOf(4, 4, 1, 3),
            intArrayOf(5, 5, 1, 3),
            intArrayOf(3, 3, 5, 7),
            intArrayOf(4, 4, 5, 7),
            intArrayOf(5, 5, 5, 7),
            intArrayOf(7, 7, 1, 7),
            intArrayOf(11, 11, 1, 7),
            intArrayOf(12, 12, 1, 7),
            intArrayOf(13, 13, 1, 7),
            intArrayOf(14, 14, 1, 7),
            intArrayOf(15, 15, 1, 7),
            intArrayOf(16, 16, 1, 7),
        )
        setBorderStyle(borderRegionsDashedBottom, BorderStyle.DASHED, Direction.BOTTOM)

        val borderRegionsThin = arrayOf(
            intArrayOf(1, 1, 1, 7),
            intArrayOf(3, 3, 1, 1),
            intArrayOf(4, 5, 1, 3),
            intArrayOf(3, 5, 5, 7),
            intArrayOf(7, 8, 1, 7),
            intArrayOf(10, 17, 1, 7),
            intArrayOf(10, 10, 1, 5),
            intArrayOf(18, 18, 1, 5),
        )
        setBorderStyle(borderRegionsThin, BorderStyle.THIN, Direction.ALL)

        val borderRegionsThick = arrayOf(
            intArrayOf(18, 18, 6, 7),
            intArrayOf(10, 10, 6, 7),
            intArrayOf(1, 1, 2, 2),
            intArrayOf(3, 3, 2, 2),
            intArrayOf(18, 18, 6, 7)
        )
        setBorderStyle(borderRegionsThick, BorderStyle.THICK, Direction.ALL)

        val borderRegionsDashedRight = arrayOf(
            intArrayOf(18, 18, 2, 3),
            intArrayOf(1, 1, 4, 5),
            intArrayOf(1, 1, 5, 6),
            intArrayOf(4, 5, 1, 2),
            intArrayOf(7, 8, 1, 1),
            intArrayOf(7, 8, 2, 2),
            intArrayOf(7, 8, 3, 3),
            intArrayOf(7, 8, 4, 4),
            intArrayOf(7, 8, 5, 5),
            intArrayOf(7, 8, 6, 6),
            intArrayOf(11, 17, 1, 1),
            intArrayOf(11, 17, 2, 2),
            intArrayOf(11, 17, 3, 3),
            intArrayOf(11, 17, 4, 4),
            intArrayOf(11, 17, 6, 6),
            intArrayOf(3, 5, 1, 1)
        )
        setBorderStyle(borderRegionsDashedRight, BorderStyle.DASHED, Direction.RIGHT)

        val borderRegionsThinRight = arrayOf(
            intArrayOf(11, 17, 5, 5),
            intArrayOf(3, 5, 5, 5)
        )
        setBorderStyle(borderRegionsThinRight, BorderStyle.THIN, Direction.RIGHT)
    }

    private fun Sheet.setCellValues() {
        val cellValues = mapOf(
            Pair(1, 1) to "접수번호",
            Pair(3, 5) to "학번",
            Pair(4, 5) to "학생",
            Pair(5, 5) to "보호자",
            Pair(7, 1) to "결석",
            Pair(7, 2) to "지각",
            Pair(7, 3) to "조퇴",
            Pair(7, 4) to "결과",
            Pair(7, 5) to "출석점수",
            Pair(7, 6) to "봉사시간",
            Pair(7, 7) to "봉사점수",
            Pair(10, 1) to "과목",
            Pair(10, 2) to "3_2학기",
            Pair(10, 3) to "3_1학기",
            Pair(10, 4) to "직전",
            Pair(10, 5) to "직전전",
            Pair(10, 6) to "교과성적",
            Pair(11, 6) to "대회",
            Pair(12, 6) to "기능사",
            Pair(13, 6) to "가산점",
            Pair(18, 6) to "총점",
            Pair(11, 1) to "국어",
            Pair(12, 1) to "사회",
            Pair(13, 1) to "역사",
            Pair(14, 1) to "수학",
            Pair(15, 1) to "과학",
            Pair(16, 1) to "기술가정",
            Pair(17, 1) to "영어",
            Pair(18, 1) to "점수"
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

    private fun insertDataIntoSheet(applicationInfoVO: ApplicationInfoVO) {
        val number = applicationInfoVO.graduation?.studentNumber
        val studentNumber = (number?.gradeNumber?.toInt()!! * 10000) + (number.classNumber.toInt() * 100) + (number.studentNumber.toInt())
        getCell(1, 2).setCellValue(applicationInfoVO.application.receiptCode.toString())
        getCell(1, 3).setCellValue(applicationService.safeGetValue(applicationInfoVO.school?.name))
        getCell(
            1,
            6
        ).setCellValue(applicationService.translateEducationalStatus(applicationInfoVO.application.educationalStatus))
        getCell(1, 7).setCellValue(applicationService.safeGetValue(applicationInfoVO.graduation?.graduateDate))
        getCell(
            3,
            1
        ).setCellValue(applicationService.translateApplicationType(applicationInfoVO.application.applicationType))
        getCell(3, 2).setCellValue(applicationInfoVO.application.applicantName)
        getCell(3, 6).setCellValue(applicationService.safeGetValue(studentNumber))
        getCell(4, 1).setCellValue(applicationService.translateIsDaejeon(applicationInfoVO.application.isDaejeon))
        getCell(4, 2).setCellValue(applicationInfoVO.application.birthDate.toString())
        getCell(4, 6).setCellValue(applicationInfoVO.application.applicantTel)
        getCell(
            5,
            1
        ).setCellValue(applicationService.translateApplicationRemark(applicationInfoVO.application.applicationRemark))
        getCell(5, 2).setCellValue(applicationService.translateSex(applicationInfoVO.application.sex))
        getCell(5, 6).setCellValue(applicationService.safeGetValue(applicationInfoVO.application.parentTel))
        getCell(8, 1).setCellValue(applicationService.safeGetValue(applicationInfoVO.graduationCase?.absenceDayCount))
        getCell(8, 2).setCellValue(applicationService.safeGetValue(applicationInfoVO.graduationCase?.latenessCount))
        getCell(8, 3).setCellValue(applicationService.safeGetValue(applicationInfoVO.graduationCase?.earlyLeaveCount))
        getCell(
            8,
            4
        ).setCellValue(applicationService.safeGetValue(applicationInfoVO.graduationCase?.lectureAbsenceCount))
        getCell(8, 5).setCellValue(applicationService.safeGetValue(applicationInfoVO.score?.attendanceScore))
        getCell(8, 6).setCellValue(applicationService.safeGetValue(applicationInfoVO.graduationCase?.volunteerTime))
        getCell(8, 7).setCellValue(applicationService.safeGetDouble(applicationInfoVO.score?.volunteerScore))
        getCell(10, 7).setCellValue(applicationService.safeGetDouble(applicationInfoVO.graduationCase?.calculateTotalGradeScore(applicationInfoVO.application.isCommon())))
        val subjectGrades = applicationInfoVO.graduationCase?.gradesPerSubject() ?: emptyMap()
        var rowIndex = 11
        subjectGrades.forEach { (subject, grades) ->
            getCell(rowIndex, 1).setCellValue(subject)
            val reversedGrades = grades.reversed()
            reversedGrades.forEachIndexed { index, grade ->
                getCell(rowIndex, index + 2).setCellValue(grade)
            }
            rowIndex++
        }

        getCell(
            11,
            7
        ).setCellValue(applicationService.translateBoolean(applicationInfoVO.graduationCase?.extraScoreItem?.hasCompetitionPrize))
        getCell(
            12,
            7
        ).setCellValue(applicationService.translateBoolean(applicationInfoVO.graduationCase?.extraScoreItem?.hasCertificate))
        getCell(13, 7).setCellValue(applicationService.safeGetDouble(applicationInfoVO.score?.extraScore))
        getCell(18, 2).setCellValue(applicationService.safeGetDouble(applicationInfoVO.score?.thirdGradeScore))
        getCell(18, 4).setCellValue(applicationService.safeGetDouble(applicationInfoVO.score?.thirdBeforeScore))
        getCell(18, 5).setCellValue(applicationService.safeGetDouble(applicationInfoVO.score?.thirdBeforeBeforeScore))
        getCell(18, 7).setCellValue(applicationService.safeGetDouble(applicationInfoVO.score?.totalScore))
    }


    enum class Direction {
        TOP,
        BOTTOM,
        LEFT,
        RIGHT,
        ALL
    }
}
