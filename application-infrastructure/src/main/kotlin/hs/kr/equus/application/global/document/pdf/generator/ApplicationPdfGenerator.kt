package hs.kr.equus.application.global.document.pdf.generator

import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfReader
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.kernel.utils.PdfMerger
import com.itextpdf.layout.Document
import hs.kr.equus.application.domain.application.model.Application
import hs.kr.equus.application.domain.application.spi.ApplicationPdfGeneratorPort
import hs.kr.equus.application.domain.score.model.Score
import hs.kr.equus.application.global.document.pdf.data.PdfDataConverter
import hs.kr.equus.application.global.document.pdf.data.TemplateFileName
import org.springframework.stereotype.Component
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.*

@Component
class ApplicationPdfGenerator(
    private val pdfProcessor: PdfProcessor,
    private val pdfDataConverter: PdfDataConverter,
    private val templateProcessor: TemplateProcessor
): ApplicationPdfGeneratorPort {

    override fun generate(application: Application, score: Score): ByteArray {
        return generateApplicationPdf(application, score)
    }

    private fun generateApplicationPdf(application: Application, score: Score): ByteArray {
        val data = pdfDataConverter.applicationToInfo(application, score)
        val templates = getTemplateFileNames(application)

        val outStream = templates.parallelStream()
            .map { template ->
                templateProcessor.convertTemplateIntoHtmlString(template, data.toMap())
            }
            .map { html ->
                pdfProcessor.convertHtmlToPdf(html)
            }
            .toArray { size -> arrayOfNulls<ByteArrayOutputStream>(size) }

        val outputStream = ByteArrayOutputStream()
        val mergedDocument = PdfDocument(PdfWriter(outputStream))
        val pdfMerger = PdfMerger(mergedDocument)
        val document = Document(mergedDocument)

        for (pdfStream in outStream) {
            val pdfDoc = getPdfDocument(pdfStream)
            mergeDocument(pdfMerger, pdfDoc)
        }

        document.close()

        return outputStream.toByteArray()
    }

    private fun getPdfDocument(outputStream: ByteArrayOutputStream?): PdfDocument? {
        return try {
            val inputStream: InputStream = ByteArrayInputStream(outputStream?.toByteArray())
            PdfDocument(PdfReader(inputStream))
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun mergeDocument(merger: PdfMerger, document: PdfDocument?) {
        if (document != null) {
            merger.merge(document, 1, document.numberOfPages)
            document.close()
        }
    }

    private fun getTemplateFileNames(application: Application): MutableList<String> {
        val result = LinkedList(
            listOf(
                TemplateFileName.APPLICATION_FOR_ADMISSION,
                TemplateFileName.PRIVACY_AGREEMENT,
                TemplateFileName.INTRODUCTION,
                TemplateFileName.NON_SMOKING,
                TemplateFileName.SMOKING_EXAMINE
            )
        )

        if (!application.isQualificationExam() && !application.isCommonApplicationType()) {
            result.add(2, TemplateFileName.RECOMMENDATION)
        }

        return result
    }
}
