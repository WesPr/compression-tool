package com.portfolio.infrastructure.boundaries.inbound.rest.error

class ErrorResponse(
    val errors: List<ErrorDTO>
) {
    fun of(errorCode: ErrorCode) =
        ErrorResponse(listOf(ErrorDTO(errorCode, null)))
}

data class ErrorDTO(
    val errorCode: ErrorCode,
    val payload: Any?
)

enum class ErrorCode{
    INVALID_FILE_TYPE
}