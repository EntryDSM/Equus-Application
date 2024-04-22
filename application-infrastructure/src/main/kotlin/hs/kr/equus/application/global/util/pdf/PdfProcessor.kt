package hs.kr.equus.application.global.util.pdf

import com.itextpdf.html2pdf.HtmlConverter
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfReader
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.kernel.utils.PdfMerger
import org.springframework.stereotype.Component
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException

@Component
class PdfProcessor(private val converterPropertiesCreator: ConverterPropertiesCreator) {

    fun concat(first: ByteArrayOutputStream, second: ByteArrayOutputStream): ByteArrayOutputStream {
        val outputStream = ByteArrayOutputStream()
        val mergedDocument = PdfDocument(PdfWriter(outputStream))
        val merger = PdfMerger(mergedDocument)

        val firstDocument = getPdfDocument(first)
        val secondDocument = getPdfDocument(second)

        mergeDocument(merger, firstDocument)
        mergeDocument(merger, secondDocument)

        mergedDocument.close()
        return outputStream
    }

    fun convertHtmlToPdf(html: String): ByteArrayOutputStream {
        val outputStream = ByteArrayOutputStream()
        HtmlConverter.convertToPdf(html, outputStream, converterPropertiesCreator.createConverterProperties())
        return outputStream
    }

    private fun getPdfDocument(outputStream: ByteArrayOutputStream): PdfDocument? {
        ByteArrayInputStream(outputStream.toByteArray()).use { inputStream ->
            return try {
                PdfDocument(PdfReader(inputStream))
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        }
    }

    private fun mergeDocument(merger: PdfMerger, document: PdfDocument?) {
        document?.let {
            merger.merge(it, 1, it.numberOfPages)
            it.close()
        }
    }
}
