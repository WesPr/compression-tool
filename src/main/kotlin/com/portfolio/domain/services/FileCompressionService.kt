package com.portfolio.domain.services

import com.portfolio.commons.BinaryUtils
import com.portfolio.commons.END_OF_FILE
import com.portfolio.commons.InvalidFileTypeException
import com.portfolio.domain.model.HuffmanTree
import org.springframework.web.multipart.MultipartFile

class FileCompressionService(
    private val fileProcessor: FileProcessor,
    private val huffmanEncoder: HuffmanEncoder,
    private val huffmanTree: HuffmanTree,
) {
    fun compressFile(file: MultipartFile): ByteArray {
        if (!fileProcessor.fileHasValidMimeType(file.originalFilename!!)) throw InvalidFileTypeException()
        val parsedFile = fileProcessor.readFile(file)
        val characterFrequencyCount = countCharacterFrequencies(parsedFile)

        val huffTree = huffmanTree.buildTree(characterFrequencyCount)
        val lookupTable = huffmanTree.buildLookupTable(huffTree)

        val header = huffmanEncoder.formatHeader(lookupTable)
        val encodedBinaryCharacters = huffmanEncoder.encodeCharacters(lookupTable, parsedFile)
        val body = huffmanEncoder.formatBody(encodedBinaryCharacters)

        return header + body
    }

    private fun countCharacterFrequencies(parsedFile: ByteArray): Map<String, Int> {
        val map = mutableMapOf<String, Int>()
        for (byte in parsedFile) {
            val paddedBinaryString = BinaryUtils.addPadding(byte)
            map[paddedBinaryString] = (map[paddedBinaryString] ?: 0) + 1
        }
        map[END_OF_FILE] = 1
        return map
    }
}
