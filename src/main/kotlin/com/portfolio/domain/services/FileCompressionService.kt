package com.portfolio.domain.services

import com.portfolio.domain.model.HuffNode
import org.springframework.web.multipart.MultipartFile
import java.util.*

class FileCompressionService(private val fileProcessor: FileProcessor, private val huffmanEncoder: HuffmanEncoder) {
    fun compressFile(file: MultipartFile): ByteArray {
        val parsedFile = fileProcessor.readFile(file)
        val characterFrequencies = countCharacterFrequencies(parsedFile)
        val huffTree = buildTree(characterFrequencies)
        val lookupTable = buildLookupTable(huffTree)
        val header = huffmanEncoder.formatHeader(lookupTable)
        val binChar = huffmanEncoder.encodeCharacters(lookupTable, parsedFile)
        val body = huffmanEncoder.formatBody(binChar)
        return header + body
    }

    private fun countCharacterFrequencies(parsedFile: ByteArray): Map<String, Int> {
        val map = mutableMapOf<String, Int>()
        for (byte in parsedFile) {
            val binaryString = byte.toUByte().toString(2)
            val paddedBinaryString = binaryString.padStart(8, '0')
            map[paddedBinaryString] = (map[paddedBinaryString] ?: 0) + 1
        }
        map["EOF"] = 1 // End of the file code
        return map
    }

    private fun buildTree(charFrequencies: Map<String, Int>): HuffNode {
        val pQueue = PriorityQueue(charFrequencies.size, HuffNode::compareTo)

        for ((char, frequency) in charFrequencies) {
            val huffNode = HuffNode(char, frequency, null, null)
            pQueue.add(huffNode)
        }

        while (pQueue.size > 1) {
            val first = pQueue.poll()
            val second = pQueue.poll()
            val newHuffNode =
                HuffNode(Char.MIN_VALUE.toString(), first.frequency + second.frequency, first, second)
            pQueue.add(newHuffNode)
        }
        return pQueue.first()
    }

    private fun buildLookupTable(root: HuffNode): Map<String, String> {
        val lookupMap: MutableMap<String, String> = mutableMapOf()
        return buildLookupTable(lookupMap, root, "")
    }

    private fun buildLookupTable(
        lookupMap: MutableMap<String, String>,
        x: HuffNode?,
        s: String
    ): Map<String, String> {
        if (x != null) {
            if (!x.isLeaf()) {
                buildLookupTable(lookupMap, x.left, s + '0')
                buildLookupTable(lookupMap, x.right, s + '1')
            } else {
                lookupMap[x.ch] = s
            }
        }
        return lookupMap
    }
}
