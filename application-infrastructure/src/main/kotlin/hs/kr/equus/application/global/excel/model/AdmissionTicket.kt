package hs.kr.equus.application.global.excel.model

import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.Workbook
import org.apache.poi.xssf.usermodel.XSSFWorkbook

class AdmissionTicket {
    private val workbook: Workbook = XSSFWorkbook()
    private val sheet: Sheet = workbook.createSheet("수험표")

    fun getWorkbook(): Workbook {
        return workbook
    }

    fun getSheet(): Sheet {
        return sheet
    }
}