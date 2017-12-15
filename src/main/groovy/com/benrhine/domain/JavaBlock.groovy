package com.benrhine.domain

import java.nio.charset.StandardCharsets
import java.security.MessageDigest

/** =================================================================================================================
 * Interchangeable hashing implementation. (Java)
 *
 * This block implementation does hashing using pure java classes
 * ================================================================================================================== */
class JavaBlock {
    Integer index
    String timestamp
    Object data
    String previousHash
    String hash = this.hashBlock()

    JavaBlock() { /* Default Constructor Unused */}

    JavaBlock(final Integer index, final String timestamp, final Object data, final String previousHash) {
        this.index = index
        this.timestamp = timestamp
        this.data = data
        this.previousHash = previousHash
        this.hash = this.hashBlock()
    }

    String hashBlock() {
        final String originalString = this.index.toString() + this.timestamp + this.data + this.previousHash
        final MessageDigest digest = MessageDigest.getInstance("SHA-256")
        final byte[] encodedHash = digest.digest(originalString.getBytes(StandardCharsets.UTF_8))

        bytesToHex(encodedHash)
    }

    private static String bytesToHex(byte[] hash) {
        StringBuffer hexString = new StringBuffer()
        for (int i = 0; i < hash.length; i++) {
            String hex = Integer.toHexString(0xff & hash[i])
            if(hex.length() == 1) hexString.append('0')
            hexString.append(hex)
        }
        hexString.toString()
    }
}
