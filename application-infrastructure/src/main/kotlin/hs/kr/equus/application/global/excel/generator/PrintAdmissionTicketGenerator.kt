package hs.kr.equus.application.global.excel.generator

import com.github.benmanes.caffeine.cache.Caffeine
import hs.kr.equus.application.domain.application.service.ApplicationService
import hs.kr.equus.application.domain.application.spi.PrintAdmissionTicketPort
import hs.kr.equus.application.domain.application.usecase.dto.vo.ApplicationInfoVO
import hs.kr.equus.application.domain.file.spi.GetObjectPort
import hs.kr.equus.application.domain.file.usecase.`object`.PathList
import hs.kr.equus.application.global.excel.exception.ExcelExceptions
import org.apache.poi.ss.usermodel.*
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.ss.util.CellReference
import org.apache.poi.xssf.streaming.SXSSFWorkbook
import org.apache.poi.xssf.usermodel.XSSFClientAnchor
import org.apache.poi.xssf.usermodel.XSSFDrawing
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.servlet.http.HttpServletResponse

@Component
class PrintAdmissionTicketGenerator(
    private val httpServletResponse: HttpServletResponse,
    private val applicationService: ApplicationService,
    private val getObjectPort: GetObjectPort
) : PrintAdmissionTicketPort {
    companion object {
        const val EXCEL_PATH = "/excel/excel-form.xlsx"
    }

    private val imageCache = Caffeine.newBuilder().expireAfterAccess(1, TimeUnit.HOURS).maximumSize(1000).build<Long, ByteArray>()

    override fun execute(response: HttpServletResponse, applications: List<ApplicationInfoVO>) {
        val sourceWorkbook = loadSourceWorkbook()
        val targetWorkbook = SXSSFWorkbook()

        val sourceSheet = sourceWorkbook.getSheetAt(0)
        val targetSheet = targetWorkbook.createSheet("수험표")

        val styleMap = createStyleMap(sourceWorkbook, targetWorkbook)

        targetSheet.setDefaultColumnWidth(13)

        val executorService = Executors.newFixedThreadPool(30)

        applications.forEach { applicationInfoVo ->
            executorService.submit {
                val application = applicationInfoVo.application
                imageCache.get(application.receiptCode!!) {
                    getObjectPort.getObject(application.photoPath!!, PathList.PHOTO)
                }
            }
        }

        executorService.shutdown()
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS)

        var currentRowIndex = 0
        applications.forEach { applicationInfoVo ->
            val application = applicationInfoVo.application
            fillApplicationData(sourceSheet, 0, applicationInfoVo, sourceWorkbook)
            copyRows(sourceSheet, targetSheet, 0, 16, currentRowIndex, styleMap)

            val imageBytes = imageCache.get(application.receiptCode!!) {
                getObjectPort.getObject(application.photoPath!!, PathList.PHOTO)
            } ?: getObjectPort.getObject(application.photoPath!!, PathList.PHOTO)

            copyImage(imageBytes, targetSheet, currentRowIndex)
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
        return resource.inputStream.use { SXSSFWorkbook(XSSFWorkbook(it)) }
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

    private fun fillApplicationData(sheet: Sheet, startRowIndex: Int, applicationInfoVo: ApplicationInfoVO, workbook: Workbook) {
        val application = applicationInfoVo.application
        val school = applicationInfoVo.school

        setValue(sheet, "E4", "")
        setValue(sheet, "E6", application.applicantName!!)
        setValue(sheet, "E8", school?.name ?: "")
        setValue(sheet, "E10", applicationService.translateIsDaejeon(application.isDaejeon))
        setValue(sheet, "E12", applicationService.translateApplicationType(application.applicationType))
        setValue(sheet, "E14", application.receiptCode.toString())
    }

    private fun copyImage(imageBytes: ByteArray, targetSheet: Sheet, targetRowIndex: Int) {
        val workbook = targetSheet.workbook
        val pictureId = workbook.addPicture(imageBytes, Workbook.PICTURE_TYPE_PNG)
        val drawing = targetSheet.createDrawingPatriarch() as XSSFDrawing
        val anchor = XSSFClientAnchor()

        anchor.setCol1(0)
        anchor.row1 = targetRowIndex + 3
        anchor.setCol2(2)
        anchor.row2 = targetRowIndex + 15

        drawing.createPicture(anchor, pictureId)
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

    private fun insertImageToCell(sheet: Sheet, rowIndex: Int, colIndex: Int, imageData: ByteArray) {
        val patriarch = sheet.createDrawingPatriarch()
        val anchor = patriarch.createAnchor(0, 0, 0, 0, colIndex, rowIndex, colIndex + 1, rowIndex + 1)

        val picture = patriarch.createPicture(anchor, loadPictureData(sheet.workbook, imageData))
        picture.resize()
    }

    private fun loadPictureData(workbook: Workbook, imageData: ByteArray): Int {
        return workbook.addPicture(imageData, Workbook.PICTURE_TYPE_JPEG)
    }
}
