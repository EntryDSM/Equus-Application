package hs.kr.equus.application.global.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.thymeleaf.TemplateEngine
import org.thymeleaf.templatemode.TemplateMode
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver


@Configuration
class PdfConfig {
    @Bean
    fun templateEngine(): TemplateEngine {
        val templateResolver = ClassLoaderTemplateResolver()
        templateResolver.suffix = ".html"
        templateResolver.templateMode = TemplateMode.HTML

        val templateEngine = TemplateEngine()
        templateEngine.setTemplateResolver(templateResolver)

        return templateEngine
    }
}