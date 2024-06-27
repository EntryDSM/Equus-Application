package hs.kr.equus.application.global.excel.exception

import hs.kr.equus.application.domain.file.presentation.exception.WebFileExceptions
import hs.kr.equus.application.global.exception.WebException

sealed class ExcelExceptions(
    override val status: Int,
    override val message: String,
) : WebException(status, message) {

    class ExcelIOException(message: String = EXCEL_IO_EXCEPTION) : ExcelExceptions(500, message)

    companion object {
        private const val EXCEL_IO_EXCEPTION = "Excel 출력에서 입출력 에러가 발생하였습니다"
    }
}