package hs.kr.equus.application.global.document.pdf.generator

import com.itextpdf.html2pdf.HtmlConverter
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.kernel.utils.PdfMerger
import hs.kr.equus.application.global.document.pdf.config.ConverterPropertiesCreator
import hs.kr.equus.application.global.document.pdf.facade.PdfDocumentFacade
import org.springframework.stereotype.Component
import java.io.ByteArrayOutputStream

@Component
class PdfProcessor(
    private val converterPropertiesCreator: ConverterPropertiesCreator,
    private val pdfDocumentFacade: PdfDocumentFacade
) {

    fun concat(first: ByteArrayOutputStream, second: ByteArrayOutputStream): ByteArrayOutputStream {
        val outputStream = ByteArrayOutputStream()
        val mergedDocument = PdfDocument(PdfWriter(outputStream))
        val merger = PdfMerger(mergedDocument)

        val firstDocument = pdfDocumentFacade.getPdfDocument(first)
        val secondDocument = pdfDocumentFacade.getPdfDocument(second)

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


    private fun mergeDocument(merger: PdfMerger, document: PdfDocument?) {
        document?.let {
            merger.merge(it, 1, it.numberOfPages)
            it.close()
        }
    }
}
