package hs.kr.equus.application.global.document.pdf.config

import com.itextpdf.html2pdf.ConverterProperties
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider
import com.itextpdf.io.font.FontProgramFactory
import hs.kr.equus.application.domain.file.exception.FileExceptions
import org.springframework.stereotype.Component
import java.io.IOException

@Component
class ConverterPropertiesCreator {

    private var fontPath: String = "/fonts/"

    fun createConverterProperties(): ConverterProperties {
        val properties = ConverterProperties()
        val fontProvider = DefaultFontProvider(false, false, false)

        Font.fonts.forEach { font ->
            try {
                val fontProgram = FontProgramFactory.createFont("$fontPath$font")
                fontProvider.addFont(fontProgram)
            } catch (e: IOException) {
                FileExceptions.PathNotFound()
            }
        }

        properties.fontProvider = fontProvider
        return properties
    }
}
