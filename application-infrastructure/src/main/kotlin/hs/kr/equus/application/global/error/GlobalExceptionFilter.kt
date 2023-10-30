package hs.kr.equus.application.global.error

import com.fasterxml.jackson.databind.ObjectMapper
import hs.kr.equus.application.global.exception.BusinessException
import hs.kr.equus.application.global.exception.GlobalExceptions
import io.sentry.Sentry
import org.springframework.http.MediaType
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException
import java.nio.charset.StandardCharsets
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class GlobalExceptionFilter(
    private val objectMapper: ObjectMapper,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        try {
            filterChain.doFilter(request, response)
        } catch (e: BusinessException) {
            println(e)
            Sentry.captureException(e)
            writerErrorCode(response, e)
        } catch (e: Exception) {
            e.printStackTrace()
            Sentry.captureException(e)
            writerErrorCode(response, GlobalExceptions.InternalServerErrorException())
        }
    }

    @Throws(IOException::class)
    private fun writerErrorCode(
        response: HttpServletResponse,
        exception: BusinessException,
    ) {
        val errorResponse = ErrorResponse(exception.status, exception.message)
        response.status = exception.status
        response.characterEncoding = StandardCharsets.UTF_8.name()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        response.writer.write(objectMapper.writeValueAsString(errorResponse))
    }
}
