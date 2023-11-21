package com.portfolio.infrastructure.boundaries.inbound.rest.error

import com.portfolio.commons.CompressorException
import com.portfolio.commons.InvalidFileTypeException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(CompressorException::class)
    fun handle(ex: CompressorException): ResponseEntity<ErrorResponse> = when (ex){
        is InvalidFileTypeException ->
            ResponseEntity.status(400).body(ErrorResponse(listOf(ErrorDTO(ErrorCode.INVALID_FILE_TYPE, ex.message))))
    }
}