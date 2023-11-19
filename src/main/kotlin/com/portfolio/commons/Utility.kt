package com.portfolio.commons

fun convertTo8bits(binary: String): String {
    val zeros = StringBuilder("")
    for (i in 0 until 8 - binary.length) zeros.append("0")
    return zeros.toString() + binary
}
