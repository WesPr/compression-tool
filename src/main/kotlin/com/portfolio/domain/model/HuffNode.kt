package com.portfolio.domain.model

data class HuffNode(
    val ch: String,
    val frequency: Int,
    val left: HuffNode?,
    val right: HuffNode?,
) : Comparable<HuffNode> {
    fun isLeaf(): Boolean = (this.left == null) && (this.right == null)

    override fun compareTo(other: HuffNode): Int = this.frequency - other.frequency
}
