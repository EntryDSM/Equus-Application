package hs.kr.equus.application.global.excel.generator

import hs.kr.equus.application.domain.application.spi.QueryApplicantCodesByIsFirstRoundPassPort
import hs.kr.equus.application.domain.application.usecase.dto.response.ApplicantCodeResponse
import hs.kr.equus.application.global.excel.ApplicantCode
import hs.kr.equus.application.global.excel.exception.ExcelExceptions
import org.apache.poi.ss.usermodel.Row
import org.springframework.stereotype.Component
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.servlet.http.HttpServletResponse

@Component
class PrintApplicantCodesGenerator(
    private val queryApplicantCodesByIsFirstRoundPassPort: QueryApplicantCodesByIsFirstRoundPassPort
){
    fun execute(response: HttpServletResponse) {
        val applicantCode = ApplicantCode()
        val sheet = applicantCode.getSheet()
        applicantCode.format()
        val applicantCodes = queryApplicantCodesByIsFirstRoundPassPort.queryApplicantCodesByIsFirstRoundPass()
        applicantCodes.forEachIndexed { index, it ->
            val row = sheet.createRow(index + 1)
            insertCode(row, it)
        }

        try {
            response.contentType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
            val formatFilename = "attachment;filename=\"지원자번호목록"
            val time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy년MM월dd일_HH시mm분"))
            val fileName = String(("$formatFilename$time.xlsx\"").toByteArray(Charsets.UTF_8), Charsets.ISO_8859_1)
            response.setHeader("Content-Disposition", fileName)

            applicantCode.getWorkbook().write(response.outputStream)
        } catch (e: IOException) {
            throw ExcelExceptions.ExcelIOException()
        }
    }

    private fun insertCode(row: Row, applicantCodeResponse: ApplicantCodeResponse) {
        row.createCell(0).setCellValue(applicantCodeResponse.examCode)
        row.createCell(1).setCellValue(applicantCodeResponse.receiptCode.toString())
        row.createCell(2).setCellValue(applicantCodeResponse.name)
    }
}
