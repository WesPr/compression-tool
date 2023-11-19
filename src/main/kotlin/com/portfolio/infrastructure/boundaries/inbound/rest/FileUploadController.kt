package com.portfolio.infrastructure.boundaries.inbound.rest

import com.portfolio.domain.services.FileCompressionService
import com.portfolio.domain.services.FileDecompressionService
import org.springframework.core.io.ByteArrayResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("v1/fileuploads")
class FileUploadController(
    private val fileCompressionService: FileCompressionService,
    private val fileDecompressionService: FileDecompressionService,
) {
    @PostMapping("/compress")
    fun compressFile(
        @RequestParam("file") file: MultipartFile,
    ): ResponseEntity<ByteArrayResource> {
        val resource = fileCompressionService.compressFile(file)

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=compressedFile.txt")
            .contentType(MediaType.TEXT_PLAIN)
            .contentLength(resource.size.toLong())
            .body(ByteArrayResource(resource))
    }

    @PostMapping("/decompress")
    fun decompressFile(
        @RequestParam("file") file: MultipartFile,
    ): ResponseEntity<ByteArrayResource> {
        val resource = fileDecompressionService.decompressFile(file)

        return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=decompressedFile.txt")
            .contentType(MediaType.TEXT_PLAIN)
            .contentLength(resource.size.toLong())
            .body(ByteArrayResource(resource))
    }
}
