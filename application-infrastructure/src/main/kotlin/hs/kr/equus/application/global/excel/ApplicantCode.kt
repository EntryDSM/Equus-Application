package hs.kr.equus.application.global.excel

import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook

class ApplicantCode {
    private val workbook: Workbook = XSSFWorkbook()
    private val sheet: Sheet = workbook.createSheet("지원자 목록")

    fun getWorkbook(): Workbook {
        return workbook
    }

    fun getSheet(): Sheet {
        return sheet
    }

    fun format() {
        val row: Row = sheet.createRow(0)
        row.createCell(0).setCellValue("수험번호")
        row.createCell(1).setCellValue("접수번호")
        row.createCell(2).setCellValue("성명")
    }
}