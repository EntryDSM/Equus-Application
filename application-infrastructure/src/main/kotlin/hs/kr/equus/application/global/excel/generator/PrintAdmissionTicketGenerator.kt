package hs.kr.equus.application.global.excel.generator

import hs.kr.equus.application.domain.application.model.Application
import hs.kr.equus.application.domain.application.service.ApplicationService
import hs.kr.equus.application.domain.application.spi.PrintAdmissionTicketPort
import hs.kr.equus.application.domain.application.usecase.dto.vo.ApplicationCodeVO
import hs.kr.equus.application.domain.application.usecase.dto.vo.ApplicationInfoVO
import hs.kr.equus.application.global.excel.exception.ExcelExceptions
import org.apache.poi.ss.usermodel.*
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.ss.util.CellReference
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.servlet.http.HttpServletResponse


@Component
class PrintAdmissionTicketGenerator(
    private val httpServletResponse: HttpServletResponse,
    private val applicationService: ApplicationService
) : PrintAdmissionTicketPort {
    companion object {
        const val EXCEL_PATH = "/excel/excel-form.xlsx"
    }

    override fun execute(response: HttpServletResponse, applications: List<ApplicationInfoVO>) {
        val sourceWorkbook = loadSourceWorkbook()
        val targetWorkbook = XSSFWorkbook()

        val sourceSheet = sourceWorkbook.getSheetAt(0)
        val targetSheet = targetWorkbook.createSheet("수험표")

        // 스타일 맵을 미리 생성
        val styleMap = createStyleMap(sourceWorkbook, targetWorkbook)

        targetSheet.setDefaultColumnWidth(13)

        var currentRowIndex = 0
        applications.forEach { application ->
            fillApplicationData(sourceSheet, 0, application)
            copyRows(sourceSheet, targetSheet, 0, 16, currentRowIndex, styleMap)
            currentRowIndex += 20
        }

        try {
            setResponseHeaders()
            targetWorkbook.write(httpServletResponse.outputStream)
        } catch (e: IOException) {
            throw ExcelExceptions.ExcelIOException().initCause(e)
        } finally {
            sourceWorkbook.close()
            targetWorkbook.close()
        }
    }

    private fun loadSourceWorkbook(): Workbook {
        val resource = ClassPathResource(EXCEL_PATH)
        return resource.inputStream.use { XSSFWorkbook(it) }
    }

    private fun createStyleMap(sourceWorkbook: Workbook, targetWorkbook: Workbook): Map<CellStyle, CellStyle> {
        return sourceWorkbook.numCellStyles.let { styleCount ->
            (0 until styleCount).associate { i ->
                val sourceStyle = sourceWorkbook.getCellStyleAt(i)
                val targetStyle = targetWorkbook.createCellStyle()
                targetStyle.cloneStyleFrom(sourceStyle)
                sourceStyle to targetStyle
            }
        }
    }

    private fun copyRows(sourceSheet: Sheet, targetSheet: Sheet, sourceStartRow: Int, sourceEndRow: Int, targetStartRow: Int, styleMap: Map<CellStyle, CellStyle>) {
        for (i in sourceStartRow..sourceEndRow) {
            val sourceRow = sourceSheet.getRow(i)
            val targetRow = targetSheet.createRow(targetStartRow + i - sourceStartRow)
            if (sourceRow != null) {
                copyRow(sourceSheet, targetSheet, sourceRow, targetRow, styleMap)
            }
        }
    }

    private fun copyRow(sourceSheet: Sheet, targetSheet: Sheet, sourceRow: Row, targetRow: Row, styleMap: Map<CellStyle, CellStyle>) {
        targetRow.height = sourceRow.height

        for (i in 0 until sourceRow.lastCellNum) {
            val oldCell = sourceRow.getCell(i)
            val newCell = targetRow.createCell(i)

            if (oldCell == null) {
                continue
            }

            copyCell(oldCell, newCell, styleMap)
        }

        // Copy merged regions that belong to the copied row
        for (i in 0 until sourceSheet.numMergedRegions) {
            val mergedRegion = sourceSheet.getMergedRegion(i)
            if (mergedRegion.firstRow == sourceRow.rowNum) {
                val newMergedRegion = CellRangeAddress(
                    targetRow.rowNum,
                    targetRow.rowNum + (mergedRegion.lastRow - mergedRegion.firstRow),
                    mergedRegion.firstColumn,
                    mergedRegion.lastColumn
                )
                targetSheet.addMergedRegion(newMergedRegion)
            }
        }
    }

    private fun copyCell(oldCell: Cell, newCell: Cell, styleMap: Map<CellStyle, CellStyle>) {
        val newStyle = styleMap[oldCell.cellStyle]
        if (newStyle != null) {
            newCell.cellStyle = newStyle
        }

        when (oldCell.cellType) {
            CellType.BLANK -> newCell.setBlank()
            CellType.BOOLEAN -> newCell.setCellValue(oldCell.booleanCellValue)
            CellType.ERROR -> newCell.setCellErrorValue(oldCell.errorCellValue)
            CellType.FORMULA -> newCell.cellFormula = oldCell.cellFormula
            CellType.NUMERIC -> newCell.setCellValue(oldCell.numericCellValue)
            CellType.STRING -> newCell.setCellValue(oldCell.richStringCellValue)
            else -> newCell.setCellValue(oldCell.stringCellValue)
        }
    }

    private fun fillApplicationData(sheet: Sheet, startRowIndex: Int, applicationInfoVo: ApplicationInfoVO) {
        val application = applicationInfoVo.application
        val graduation = applicationInfoVo.graduation
        val school = applicationInfoVo.school
        setValue(sheet, "E4", "")
        setValue(sheet, "E6", application.applicantName!!)
        setValue(sheet, "E8", school?.name ?: "")
        setValue(sheet, "E10", applicationService.translateIsDaejeon(application.isDaejeon))
        setValue(sheet, "E12", applicationService.translateApplicationType(application.applicationType))
        setValue(sheet, "E14", application.receiptCode.toString())
    }

    private fun setValue(sheet: Sheet, position: String, value: String) {
        val ref = CellReference(position)
        val r = sheet.getRow(ref.row)
        if (r != null) {
            val c = r.getCell(ref.col.toInt())
            c.setCellValue(value)
        }
    }

    private fun setResponseHeaders() {
        httpServletResponse.contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
        val formatFilename = "attachment;filename=\"수험표"
        val time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy년MM월dd일_HH시mm분"))
        val fileName = String(("$formatFilename$time.xlsx\"").toByteArray(Charsets.UTF_8), Charsets.ISO_8859_1)
        httpServletResponse.setHeader("Content-Disposition", fileName)
    }
}