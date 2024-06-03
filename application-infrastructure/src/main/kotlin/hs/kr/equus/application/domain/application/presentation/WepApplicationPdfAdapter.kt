package hs.kr.equus.application.domain.application.presentation

import hs.kr.equus.application.domain.application.usecase.ApplicationPdfUseCase
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/pdf")
class WepApplicationPdfAdapter(
    private val applicationPdfUseCase: ApplicationPdfUseCase
) {
    @GetMapping(produces = [MediaType.APPLICATION_PDF_VALUE])
    fun pdf(): ByteArray = applicationPdfUseCase.getPreviewApplicationPdf()
}