package com.portfolio.domain.services

import com.portfolio.commons.convertTo8bits

class HuffmanDecoder {
    fun retrieveEncodedHeaderAndBodyByteArray(parsedFile: ByteArray): Pair<String, ByteArray> {
        var endOfHeader: String = convertTo8bits(Integer.toBinaryString('\n'.code))
        val header = StringBuilder()
        var doubleKey = StringBuilder()
        endOfHeader += endOfHeader
        var remainingParsedFileIndex = 0
        for (b in parsedFile) {
            val section =
                String.format(
                    "%8s",
                    Integer.toBinaryString(b.toInt() and 0xFF),
                ).replace(' ', '0')
            doubleKey.append(section)
            header.append(section.toInt(2).toChar())
            if (doubleKey.length == 16) {
                if (doubleKey.toString() == endOfHeader) {
                    break
                }
                doubleKey = StringBuilder(doubleKey.substring(8))
                remainingParsedFileIndex++
            }
        }
        val remainingParsedFile =
            parsedFile.copyOfRange(remainingParsedFileIndex + 2, parsedFile.size) //advanced index twice
        return header.toString() to remainingParsedFile
    }

    fun retrieveCodewords(header: String): Map<String, String> {
        val lines = header.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val codewords = mutableMapOf<String, String>()
        for (i in lines.indices) {
            val codes = lines[i].split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (codes.size > 1) codewords[codes[1]] = codes[0]
        }
        return codewords
    }

    fun retrieveEncodedBody(parsedFile: ByteArray): String {
        val body = StringBuilder()
        for (b in parsedFile) {
            val toRtn =
                String.format(
                    "%8s",
                    Integer.toBinaryString(b.toInt() and 0xFF),
                ).replace(' ', '0')
            body.append(toRtn)
        }
        return body.toString()
    }
}
