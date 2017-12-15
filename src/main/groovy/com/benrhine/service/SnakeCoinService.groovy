package com.benrhine.service

import com.benrhine.domain.GuavaBlock
import com.benrhine.domain.JavaBlock
import groovy.json.JsonSlurper

class SnakeCoinService {

    GuavaBlock createGenesisGuavaBlock() {
        final String now = new Date().format("yyyyMMdd-HH:mm:ss.SSS", TimeZone.getTimeZone('UTC')).toString()
        final JsonSlurper jsonSlurper = new JsonSlurper()
        final data = jsonSlurper.parseText('{"proofOfWork": 9, "transactions": "none"}')
        new GuavaBlock(0, now, data, '0')
    }

    GuavaBlock nextGuavaBlock(final GuavaBlock previousBlock) {
        final Integer currentIndex = previousBlock.index + 1
        final String currentTimestamp = new Date().format("yyyyMMdd-HH:mm:ss.SSS", TimeZone.getTimeZone('UTC')).toString()
        final String currentData = 'Hey! Im block ' + currentIndex.toString()
        final String currentHash = previousBlock.hash

        new GuavaBlock(currentIndex, currentTimestamp, currentData, currentHash)
    }

    JavaBlock createGenesisJavaBlock() {
        final String now = new Date().format("yyyyMMdd-HH:mm:ss.SSS", TimeZone.getTimeZone('UTC')).toString()
        final JsonSlurper jsonSlurper = new JsonSlurper()
        final data = jsonSlurper.parseText('{"proofOfWork": 9, "transactions": "none"}')
        new JavaBlock(0, now, data, '0')
    }

    JavaBlock nextJavaBlock(final JavaBlock previousBlock) {
        final Integer currentIndex = previousBlock.index + 1
        final String currentTimestamp = new Date().format("yyyyMMdd-HH:mm:ss.SSS", TimeZone.getTimeZone('UTC')).toString()
        final String currentData = 'Hey! Im block ' + currentIndex.toString()
        final String currentHash = previousBlock.hash

        new JavaBlock(currentIndex, currentTimestamp, currentData, currentHash)
    }
}
