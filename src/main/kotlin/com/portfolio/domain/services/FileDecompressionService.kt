package com.portfolio.domain.services

import com.portfolio.commons.END_OF_FILE
import org.springframework.web.multipart.MultipartFile

class FileDecompressionService(private val fileProcessor: FileProcessor, private val huffmanDecoder: HuffmanDecoder) {
    fun decompressFile(file: MultipartFile): ByteArray {
        val parsedFile = fileProcessor.readFile(file)

        val (binaryHeader, byteBody) = huffmanDecoder.retrieveHeaderAndBody(parsedFile)
        val binaryBody = huffmanDecoder.retrieveBinaryBody(byteBody)
        val binaryLookupTable = huffmanDecoder.retrieveBinaryLookupTable(binaryHeader)

        val section = StringBuilder()
        var j = 0
        for (i in 1 until binaryBody.length) {
            val code = searchForCode(binaryBody.substring(j, i), binaryLookupTable)
            if (code == END_OF_FILE) break
            if (code != null) {
                section.append(code)
                j = i
            }
        }
        return huffmanDecoder.decodeBody(section)
    }

    private fun searchForCode(
        code: String,
        codewords: Map<String, String>,
    ): String? {
        return if (codewords.containsKey(code)) codewords[code] else null
    }
}
