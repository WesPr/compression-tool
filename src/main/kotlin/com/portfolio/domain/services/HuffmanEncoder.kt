package com.portfolio.domain.services

import com.portfolio.commons.convertTo8bits

class HuffmanEncoder {
    fun formatHeader(codewords: Map<String, String>): ByteArray {
        val stringHeader = StringBuilder()
        val header8Bits = StringBuilder()
        for ((key, value) in codewords) stringHeader.append("$key,$value\n")
        val headerSection = stringHeader.append("\n").toString()
        for (c in headerSection) {
            header8Bits.append((convertTo8bits(Integer.toBinaryString(c.code))))
        }
        return convert8bitsToByteArray(header8Bits.toString())
    }

    fun formatBody(binChar: StringBuilder): ByteArray {
        val byteList = mutableListOf<Byte>()
        while (binChar.length > 8) {
            val readMax = 8 * (binChar.length / 8)
            byteList.addAll(convert8bitsToByteArray(binChar.substring(0, readMax)).toList())
            binChar.delete(0, readMax)
        }
        if (binChar.isNotEmpty()) {
            for (i in binChar.length until 8) {
                binChar.append("0")
            }
            byteList.addAll(convert8bitsToByteArray(binChar.toString()).toList())
        }
        return byteList.toByteArray()
    }

    private fun convert8bitsToByteArray(bits: String): ByteArray {
        val strLen: Int = bits.length
        val toWrite = ByteArray(strLen / 8)
        for (i in 0 until strLen / 8) {
            toWrite[i] = bits.substring(i * 8, (i + 1) * 8).toInt(2).toByte()
        }
        return toWrite
    }

    fun encodeCharacters(
        codes: Map<String, String>,
        parsedFile: ByteArray,
    ): StringBuilder {
        val binChar = StringBuilder()
        for (byte in parsedFile) {
            val binaryString = byte.toUByte().toString(2)
            val paddedBinaryString = binaryString.padStart(8, '0')
            binChar.append(codes[paddedBinaryString])
        }
        return binChar.append(codes["EOF"])
    }
}
