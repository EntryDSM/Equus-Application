package hs.kr.equus.application.global.excel.model

import org.apache.poi.ss.usermodel.*
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.xssf.usermodel.XSSFWorkbook

class ApplicationCheckList {
    private val workbook: Workbook = XSSFWorkbook()
    private val sheet: Sheet = workbook.createSheet("점검표")

     private val borderedCellStyle: CellStyle = workbook.createCellStyle().apply {
        borderTop = BorderStyle.THIN
        borderBottom = BorderStyle.THIN
        borderLeft = BorderStyle.THIN
        borderRight = BorderStyle.THIN
        alignment = HorizontalAlignment.CENTER
        verticalAlignment = VerticalAlignment.CENTER
    }

    private val font: Font = workbook.createFont().apply {
        fontName = "Arial"
        fontHeightInPoints = 10
        bold = true
    }

    init {
        borderedCellStyle.setFont(font)
    }

    fun getWorkbook(): Workbook = workbook
    fun getSheet(): Sheet = sheet

    fun formatSheet() {
        val row6 = sheet.createRow(6)
        row6.createCell(0).setCellValue("결석")
        row6.createCell(1).setCellValue("지각")
        row6.createCell(3).setCellValue("조퇴")
        row6.createCell(4).setCellValue("결과")
        row6.createCell(5).setCellValue("출석점수")
        row6.createCell(6).setCellValue("봉사시간")
        row6.createCell(7).setCellValue("봉사점수")
        val row7 = sheet.createRow(7)
        row7.createCell(0).setCellValue("과목")
        row7.createCell(1).setCellValue("3_2학기")
        row7.createCell(2).setCellValue("3_1학기")
        row7.createCell(3).setCellValue("직전")
        row7.createCell(4).setCellValue("직직전")
        row7.createCell(5).setCellValue("교과성적")
        sheet.createRow(8).createCell(0).setCellValue("국어")
        sheet.createRow(8).createCell(5).setCellValue("대회")
        sheet.createRow(9).createCell(0).setCellValue("사회")
        sheet.createRow(9).createCell(0).setCellValue("기능사")
        sheet.createRow(10).createCell(0).setCellValue("역사")
        sheet.createRow(10).createCell(0).setCellValue("가산점")
        sheet.createRow(11).createCell(0).setCellValue("수학")
        sheet.createRow(12).createCell(0).setCellValue("과학")
        sheet.createRow(13).createCell(0).setCellValue("기술가정")
        sheet.createRow(14).createCell(0).setCellValue("영어")
        sheet.createRow(15).createCell(0).setCellValue("점수")
        sheet.createRow(15).createCell(5).setCellValue("총점")

        sheet.addMergedRegion(CellRangeAddress(0, 0, 2, 3))
        sheet.addMergedRegion(CellRangeAddress(1, 1, 2, 3))
        sheet.addMergedRegion(CellRangeAddress(2, 2, 2, 3))
        sheet.addMergedRegion(CellRangeAddress(3, 3, 2, 3))
        for (i in 0..6) {
            sheet.setColumnWidth(i, 4000)
        }
    }
}
