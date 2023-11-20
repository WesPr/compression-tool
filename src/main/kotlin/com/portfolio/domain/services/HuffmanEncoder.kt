package com.portfolio.domain.services

import com.portfolio.commons.BinaryUtils
import com.portfolio.commons.END_OF_FILE

class HuffmanEncoder {
    fun formatHeader(lookupTable: Map<String, String>): ByteArray {
        val stringHeader = StringBuilder()
        val header8Bits = StringBuilder()

        for ((key, value) in lookupTable) stringHeader.append("$key,$value\n")

        val headerSection = stringHeader.append("\n").toString()
        for (c in headerSection) {
            header8Bits.append((BinaryUtils.convertTo8bits(Integer.toBinaryString(c.code))))
        }
        return BinaryUtils.convert8bitsToByteArray(header8Bits.toString())
    }

    fun encodeCharacters(
        codes: Map<String, String>,
        parsedFile: ByteArray,
    ): StringBuilder {
        val binaryCharacters = StringBuilder()

        for (byte in parsedFile) {
            binaryCharacters.append(codes[BinaryUtils.addPadding(byte)])
        }
        return binaryCharacters.append(codes[END_OF_FILE])
    }

    fun formatBody(encodedBinaryCharacters: StringBuilder): ByteArray {
        val byteList = mutableListOf<Byte>()
        while (encodedBinaryCharacters.length > 8) {
            val readMax = 8 * (encodedBinaryCharacters.length / 8)
            byteList.addAll(BinaryUtils.convert8bitsToByteArray(encodedBinaryCharacters.substring(0, readMax)).toList())
            encodedBinaryCharacters.delete(0, readMax)
        }
        if (encodedBinaryCharacters.isNotEmpty()) {
            for (i in encodedBinaryCharacters.length until 8) {
                encodedBinaryCharacters.append("0")
            }
            byteList.addAll(BinaryUtils.convert8bitsToByteArray(encodedBinaryCharacters.toString()).toList())
        }
        return byteList.toByteArray()
    }
}
