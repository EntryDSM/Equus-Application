package hs.kr.equus.application.global.excel.exception

import hs.kr.equus.application.domain.file.presentation.exception.WebFileExceptions
import hs.kr.equus.application.global.exception.WebException

sealed class ExcelExceptions(
    override val status: Int,
    override val message: String,
) : WebException(status, message) {

    class ExcelOException(message: String = EXCEL_IO_EXCEPTION) : ExcelExceptions(400, message)

    companion object {
        private const val EXCEL_IO_EXCEPTION = "엑셀 파일을 다운로드 할 수 없습니다"
    }
}