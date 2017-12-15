package com.benrhine.service

import com.benrhine.domain.GuavaBlock
import groovy.json.JsonSlurper
import spark.Request
import spark.Response

import static spark.Spark.*

class BlockchainService {

    def transaction(final Request request, final Response response, List transactions) {
        final JsonSlurper jsonSlurper = new JsonSlurper()
        final newTransaction = jsonSlurper.parseText(request.body())
        println request.body().class
        println request.body()
        println newTransaction.class
        println newTransaction
        transactions.add(newTransaction)

        response.status(200)
        println "New transaction\n"
        println "FROM: ${newTransaction.from}\n"
        println "TO: ${newTransaction.to}\n"
        println "AMOUNT: ${newTransaction.amount}\n"
    }

    def getBlocks(blockChain) {

        for(GuavaBlock block:blockChain) {
            println block
        }
        blockChain // return
    }

    def findNewChains(peerNodes) {
        def otherChains = []

        for(def node:peerNodes) {
            final block = get("${node}/blocks", {})

            otherChains.add(block)
        }
        return otherChains
    }

    def consensus() {
        def otherChains = this.findNewChains(peerNodes)


    }

    def proofOfWork(lastProof) {

    }

    def mine() {

    }
}
