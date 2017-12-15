package com.benrhine.domain

import com.google.common.hash.Hashing

import java.nio.charset.StandardCharsets

/** =================================================================================================================
 * Interchangeable hashing implementation. (Guava)
 *
 * This block implementation does hashing using Google's Guava library as it is a bit more succinct.
 * ================================================================================================================== */
class GuavaBlock {
    Integer index
    String timestamp
    Object data
    String previousHash
    String hash = this.hashBlock()

    GuavaBlock() { /* Default Constructor Unused */}

    GuavaBlock(final Integer index, final String timestamp, final Object data, final String previousHash) {
        this.index = index
        this.timestamp = timestamp
        this.data = data
        this.previousHash = previousHash
        this.hash = this.hashBlock()
    }

    String hashBlock() {
        Hashing.sha256().hashString(this.index.toString() + this.timestamp + this.data + this.previousHash, StandardCharsets.UTF_8).toString()
    }

    String toString() {
        "GuavaBlock[index: ${this.index}, timestamp: ${this.timestamp}, data: ${this.data}, previousHash: ${this.previousHash}, hash: ${this.hash}]"
    }
}
