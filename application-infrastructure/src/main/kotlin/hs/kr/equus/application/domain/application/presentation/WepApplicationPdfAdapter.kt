package hs.kr.equus.application.domain.application.presentation

import hs.kr.equus.application.domain.application.usecase.ApplicationPdfUseCase
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.nio.charset.StandardCharsets
import javax.servlet.http.HttpServletResponse

@RestController
@RequestMapping("/pdf")
class WepApplicationPdfAdapter(
    private val applicationPdfUseCase: ApplicationPdfUseCase
) {
    @GetMapping("/preview", produces = [MediaType.APPLICATION_PDF_VALUE])
    fun previewPdf(): ByteArray = applicationPdfUseCase.getPreviewApplicationPdf()

    @GetMapping("/final", produces = [MediaType.APPLICATION_PDF_VALUE])
    fun finalPdf(response: HttpServletResponse): ByteArray {
        response.setHeader("Content-Disposition", "attachment; filename=\"${encodeFileName()}.pdf\"")
        return applicationPdfUseCase.getFinalApplicationPdf()
    }

    private fun encodeFileName(): String {
        return String(FILE_NAME.toByteArray(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1)
    }
    companion object {
        const val FILE_NAME = "entry"
    }
}
