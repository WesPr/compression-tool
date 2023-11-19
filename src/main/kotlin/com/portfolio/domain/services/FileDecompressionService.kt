package com.portfolio.domain.services

import org.springframework.web.multipart.MultipartFile

class FileDecompressionService(private val fileProcessor: FileProcessor, private val huffmanDecoder: HuffmanDecoder) {
    fun decompressFile(file: MultipartFile): ByteArray {
        val parsedFile = fileProcessor.readFile(file)
        val (header, bodyBytes) = huffmanDecoder.retrieveEncodedHeaderAndBodyByteArray(parsedFile)
        val body = huffmanDecoder.retrieveEncodedBody(bodyBytes)
        val codeWords = huffmanDecoder.retrieveCodewords(header)
        val section = StringBuilder()
        var i = 1
        var j = 0
        while (i < body.length) {
            val code = searchForCode(body.substring(j, i), codeWords)
            if (code == "EOF") break
            if (code != null) {
                section.append(code)
                j = i
            }
            i++
        }
        return decodeBody(section)
    }

    private fun searchForCode(
        code: String,
        codewords: Map<String, String>
    ): String? {
        return if (codewords.containsKey(code)) codewords[code] else null
    }

    private fun decodeBody(section: StringBuilder): ByteArray {
        val byteArray = ByteArray(section.length / 8)
        while (section.length > 8) {
            val readMax: Int = 8 * (section.length / 8)
            for (i in 0 until section.length / 8) {
                byteArray[i] = section.substring(i * 8, (i + 1) * 8).toInt(2).toByte()
            }
            section.delete(0, readMax)
        }
        if (section.isNotEmpty()) {
            for (x in section.length..7) section.append("0")
            for (i in 0 until section.length / 8) {
                byteArray[i] = section.substring(i * 8, (i + 1) * 8).toInt(2).toByte()
            }
        }
        return byteArray
    }
}
