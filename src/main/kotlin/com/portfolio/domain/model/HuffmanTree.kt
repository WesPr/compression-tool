package com.portfolio.domain.model

import java.util.*

class HuffmanTree {
    fun buildTree(charFrequencies: Map<String, Int>): HuffNode {
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

    fun buildLookupTable(root: HuffNode): Map<String, String> {
        val lookupMap: MutableMap<String, String> = mutableMapOf()
        return buildLookupTable(lookupMap, root, "")
    }

    private fun buildLookupTable(
        lookupMap: MutableMap<String, String>,
        x: HuffNode?,
        s: String,
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

data class HuffNode(
    val ch: String,
    val frequency: Int,
    val left: HuffNode?,
    val right: HuffNode?,
) : Comparable<HuffNode> {
    fun isLeaf(): Boolean = (this.left == null) && (this.right == null)

    override fun compareTo(other: HuffNode): Int = this.frequency - other.frequency
}
