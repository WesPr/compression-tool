package com.portfolio.commons

object BinaryUtils {
    fun convertTo8bits(binary: String): String {
        val zeros = StringBuilder("")
        for (i in 0 until 8 - binary.length) zeros.append("0")
        return zeros.toString() + binary
    }

    fun convert8bitsToByteArray(bits: String): ByteArray {
        val strLen: Int = bits.length
        val toWrite = ByteArray(strLen / 8)
        for (i in 0 until strLen / 8) {
            toWrite[i] = bits.substring(i * 8, (i + 1) * 8).toInt(2).toByte()
        }
        return toWrite
    }

    fun addPadding(byte: Byte): String  {
        val binaryString = byte.toUByte().toString(2)
        return binaryString.padStart(8, '0')
    }

    fun convertByteToBinaryString(byte: Byte): String  {
        return String.format(
            "%8s",
            Integer.toBinaryString(byte.toInt() and 0xFF),
        ).replace(' ', '0')
    }
}
