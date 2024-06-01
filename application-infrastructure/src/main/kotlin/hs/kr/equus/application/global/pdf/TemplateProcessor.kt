package hs.kr.equus.application.global.pdf

import org.springframework.stereotype.Component
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context

@Component
class TemplateProcessor(
    private val templateEngine: TemplateEngine
) {
    fun convertTemplateIntoHtmlString(template: String?, data: MutableMap<String, Any>?): String {
        val context = Context()
        context.setVariables(data)
        return templateEngine.process(template, context)
    }
}