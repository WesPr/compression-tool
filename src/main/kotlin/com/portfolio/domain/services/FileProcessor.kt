package com.portfolio.domain.services

import com.portfolio.commons.InvalidFileTypeException
import org.springframework.web.multipart.MultipartFile

class FileProcessor() {
    fun readFile(file: MultipartFile): ByteArray {
        if (!fileHasValidMimeType(file.originalFilename!!)) throw InvalidFileTypeException()
        return file.bytes
    }

    private fun fileHasValidMimeType(file: String): Boolean = file.lowercase().endsWith("txt")
}
