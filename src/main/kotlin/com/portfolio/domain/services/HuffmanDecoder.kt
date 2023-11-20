package com.portfolio.domain.services

import com.portfolio.commons.BinaryUtils

class HuffmanDecoder {
    fun decodeBody(section: StringBuilder): ByteArray {
        return BinaryUtils.convert8bitsToByteArray(section.toString())
    }

    fun retrieveHeaderAndBody(parsedFile: ByteArray): Pair<String, ByteArray> {
        val endOfHeader: String = BinaryUtils.convertTo8bits(Integer.toBinaryString('\n'.code)).repeat(2)

        val header = StringBuilder()
        var doubleKey = StringBuilder()
        var remainingParsedFileIndex = 0

        for (byte in parsedFile) {
            val binaryString = BinaryUtils.convertByteToBinaryString(byte)
            doubleKey.append(binaryString)
            header.append(binaryString.toInt(2).toChar())
            if (doubleKey.length == 16) {
                if (doubleKey.toString() == endOfHeader) {
                    break
                }
                doubleKey = StringBuilder(doubleKey.substring(8))
                remainingParsedFileIndex++
            }
        }

        val body = parsedFile.copyOfRange(remainingParsedFileIndex + 2, parsedFile.size)

        return header.toString() to body
    }

    fun retrieveBinaryBody(byteBody: ByteArray): String {
        val body = StringBuilder()
        byteBody.forEach { byte ->
            body.append(BinaryUtils.convertByteToBinaryString(byte))
        }
        return body.toString()
    }

    fun retrieveBinaryLookupTable(header: String): Map<String, String> {
        val lines = header.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val codewords = mutableMapOf<String, String>()
        for (i in lines.indices) {
            val codes = lines[i].split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (codes.size > 1) codewords[codes[1]] = codes[0]
        }
        return codewords
    }
}
