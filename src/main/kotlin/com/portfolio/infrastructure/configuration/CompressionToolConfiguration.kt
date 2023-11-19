package com.portfolio.infrastructure.configuration

import com.portfolio.domain.services.FileCompressionService
import com.portfolio.domain.services.FileDecompressionService
import com.portfolio.domain.services.FileProcessor
import com.portfolio.domain.services.HuffmanDecoder
import com.portfolio.domain.services.HuffmanEncoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CompressionToolConfiguration {
    @Bean
    fun fileCompressionService(
        fileProcessor: FileProcessor,
        huffmanEncoder: HuffmanEncoder,
    ): FileCompressionService {
        return FileCompressionService(fileProcessor, huffmanEncoder)
    }

    @Bean
    fun fileDecompressionService(
        fileProcessor: FileProcessor,
        huffmanDecoder: HuffmanDecoder,
    ): FileDecompressionService {
        return FileDecompressionService(fileProcessor, huffmanDecoder)
    }

    @Bean
    fun fileProcessor(): FileProcessor {
        return FileProcessor()
    }

    @Bean
    fun huffmanEncoder(): HuffmanEncoder {
        return HuffmanEncoder()
    }

    @Bean
    fun huffmanDecoder(): HuffmanDecoder {
        return HuffmanDecoder()
    }
}
