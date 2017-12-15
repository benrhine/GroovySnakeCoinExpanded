import com.benrhine.domain.GuavaBlock
import com.benrhine.domain.JavaBlock
import com.benrhine.service.BlockchainService
import com.benrhine.service.SnakeCoinService
import groovy.json.JsonSlurper
import spark.Request
import spark.Response

import static spark.Spark.*

/** =================================================================================================================
 * Simple Blockchain implementation written in Groovy. Current block hashing choice makes use of Google's Guava
 * library but I have included a pure Java hashing implementation as well. If you wish to try the pure Java hashing
 * comment out createGuavaBlockChain in main and un-comment createJavaBlockChain.
 *
 * To Execute this application
 *      ./gradlew build
 *      ./gradlew run
 * ================================================================================================================== */
class App {
    private final static Integer numberOfBlocksToAdd = 20
    private static thisNodesTransactions = []
    private static  peerNodes = []
    private static mining = true
    private static List<GuavaBlock> blockChain = []
    private final static String minerAddress = "q3nf394hjg-random-miner-address-34nf3i4nflkn3oi"

    String getGreeting() {
        return "Welcome to Snake Coin. Now creating ${numberOfBlocksToAdd} SnakeCoins"
    }

    private List<GuavaBlock> createGenesisBlock() {
        SnakeCoinService snakeCoinService = new SnakeCoinService()
        List<GuavaBlock> blockChain = [snakeCoinService.createGenesisGuavaBlock()]
        println "Genesis Block has been added to the blockchain!\nGenesis Hash: ${blockChain[0].hash}\n"

        blockChain // return by default
    }

    private findNewChains() {
        def otherChains = []

        for(def node:peerNodes) {
            final block = get("${node}/blocks", {})

            otherChains.add(block)
        }
        otherChains // return
    }

    private consensus() {
        def otherChains = findNewChains()
        def longestChain = blockChain

        for(def chain:otherChains) {
            if(longestChain.size() < chain.size()) {
                longestChain = chain
            }
        }
        blockChain = longestChain
    }

    private static proofOfWork(Integer lastProof) {
        def incrementor = lastProof + 1

        while((incrementor % 9 != 0) && (incrementor % lastProof != 0)) {
            incrementor += 1
        }
        incrementor // return
    }

    static void main(String[] args) {

        final range = 0..(numberOfBlocksToAdd-1)
        final App app = new App()
        println app.greeting

        BlockchainService blockchainService = new BlockchainService()

        blockChain = app.createGenesisBlock()


        get("/hello", {req, res -> "Hello World"})
        post("/txion", {request, response ->
            //transaction(request, response)
            blockchainService.transaction(request, response, thisNodesTransactions) // pass by reference

            println thisNodesTransactions.toString()
            return "Transaction submission successful"
        })


        get("/blocks", {request, response ->

            return blockchainService.getBlocks(blockChain)  // this one may need work
        })
        get("/mine", {request, response ->
            //response.status(200)
            return mine()
            //return "Mining Successful"
        })
    }

    private static mine() {
        def lastBlock = blockChain.get(blockChain.size() - 1)
        Integer lastProof = lastBlock.data.proofOfWork

        println lastBlock
        println lastProof

        def proof = proofOfWork(lastProof)

        println proof

        //'{"proofOfWork": 9, "transactions": "none"}'
        final JsonSlurper jsonSlurper = new JsonSlurper()
        final String trx = '{"from": "network", "to": "' + minerAddress + '", "amount": 1}'
        println trx
        final transaction = jsonSlurper.parseText(trx)

        println transaction

        thisNodesTransactions.add(transaction)

        def newDataBlock = [:]
        newDataBlock.proofOfWork = proof
        newDataBlock.transactions = thisNodesTransactions

        def newBlockIndex = lastBlock.index + 1
        def newBlockTimestamp = new Date().format("yyyyMMdd-HH:mm:ss.SSS", TimeZone.getTimeZone('UTC')).toString()
        def lastBlockHash = lastBlock.hash

        thisNodesTransactions.clear()

        println thisNodesTransactions

        def minedBlock = new GuavaBlock(index: newBlockIndex, timestamp: newBlockTimestamp, data: newDataBlock, hash: lastBlockHash)

        println minedBlock.toString()

        blockChain.add(minedBlock)

        println blockChain
        minedBlock.toString() //return
    }
}
