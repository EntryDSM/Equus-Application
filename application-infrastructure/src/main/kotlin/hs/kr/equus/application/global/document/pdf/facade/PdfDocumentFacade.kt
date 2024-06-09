package hs.kr.equus.application.global.document.pdf.facade

import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfReader
import org.springframework.stereotype.Component
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

@Component
class PdfDocumentFacade {
    fun getPdfDocument(outputStream: ByteArrayOutputStream): PdfDocument {
        ByteArrayInputStream(outputStream.toByteArray()).use { inputStream ->
            return PdfDocument(PdfReader(inputStream))
        }
    }
}