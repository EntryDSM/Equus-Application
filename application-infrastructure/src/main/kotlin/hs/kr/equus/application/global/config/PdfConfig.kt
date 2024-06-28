package hs.kr.equus.application.global.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.thymeleaf.TemplateEngine
import org.thymeleaf.spring5.SpringTemplateEngine
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver
import org.thymeleaf.templatemode.TemplateMode


@Configuration
class PdfConfig {
    @Bean
    fun templateEngine(): TemplateEngine {
        val templateEngine: TemplateEngine = SpringTemplateEngine()
        templateEngine.addTemplateResolver(springResourceTemplateResolver())
        return templateEngine
    }

    @Bean
    fun springResourceTemplateResolver(): SpringResourceTemplateResolver {
        val springResourceTemplateResolver = SpringResourceTemplateResolver()
        springResourceTemplateResolver.order = 1
        springResourceTemplateResolver.prefix = "classpath:templates"
        springResourceTemplateResolver.suffix = ".html"
        springResourceTemplateResolver.templateMode = TemplateMode.HTML
        springResourceTemplateResolver.characterEncoding = "UTF-8"
        springResourceTemplateResolver.isCacheable = false

        return springResourceTemplateResolver
    }
}