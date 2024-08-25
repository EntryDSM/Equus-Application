package hs.kr.equus.application.global.error

import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import hs.kr.equus.application.global.exception.BusinessException
import hs.kr.equus.application.global.feign.client.dto.response.SchoolInfoElement
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler(
    private val messageSource: MessageSource,
) {
    @ExceptionHandler(BusinessException::class)
    fun handlingEquusException(e: BusinessException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(e.status, e.message),
            HttpStatus.valueOf(e.status),
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun validatorExceptionHandler(e: MethodArgumentNotValidException): ResponseEntity<ErrorResponse> {
        return ResponseEntity(
            ErrorResponse(
                400,
                e.bindingResult.allErrors[0].defaultMessage,
            ),
            HttpStatus.BAD_REQUEST,
        )
    }
    @ExceptionHandler(MissingKotlinParameterException::class)
    fun handleMissingKotlinParameterException(ex: MissingKotlinParameterException): ResponseEntity<Any> {
        val emptyListResponse = emptyList<Any>()
        return ResponseEntity.status(HttpStatus.OK).body(emptyListResponse)
    }
}
