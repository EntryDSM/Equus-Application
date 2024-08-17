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
        sheet.addMergedRegion(CellRangeAddress(0, 0, 2, 3))
        sheet.addMergedRegion(CellRangeAddress(1, 1, 2, 3))
        sheet.addMergedRegion(CellRangeAddress(2, 2, 2, 3))
        sheet.addMergedRegion(CellRangeAddress(3, 3, 2, 3))

        for (i in 0..6) {
            sheet.setColumnWidth(i, 4000)
        }
    }
}
