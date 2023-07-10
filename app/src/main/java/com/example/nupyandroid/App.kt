package com.example.nupyandroid

import android.os.Build
import androidx.annotation.RequiresApi
import com.nftco.flow.sdk.*
import com.nftco.flow.sdk.cadence.AddressField
import com.nftco.flow.sdk.cadence.StringField
import com.nftco.flow.sdk.cadence.UFix64NumberField
import com.nftco.flow.sdk.crypto.Crypto
import java.math.BigDecimal

//https://github.com/onflow/flow-java-client-example/blob/master/src/main/kotlin/org/onflow/examples/kotlin/App.kt
internal class App(host: String, port: Int, privateKeyHex: String) {

    private val accessAPI = Flow.newAccessApi(host, port)
    private val privateKey = Crypto.decodePrivateKey(privateKeyHex)

    private val latestBlockID: FlowId get() = accessAPI.getLatestBlockHeader().id

    fun getAccount(address: FlowAddress): FlowAccount = accessAPI.getAccountAtLatestBlock(address)!!

    fun getAccountBalance(address: FlowAddress): BigDecimal {
        val account = getAccount(address)
        return account.balance
    }

    private fun getAccountKey(address: FlowAddress, keyIndex: Int): FlowAccountKey {
        val account = getAccount(address)
        return account.keys[keyIndex]
    }

    private fun getTransactionResult(txID: FlowId): FlowTransactionResult {
        val txResult = accessAPI.getTransactionResultById(txID)!!
        if (txResult.errorMessage.isNotEmpty()) {
            throw Exception(txResult.errorMessage)
        }
        return txResult
    }

    private fun waitForSeal(txID: FlowId): FlowTransactionResult {
        var txResult: FlowTransactionResult
        while (true) {
            txResult = getTransactionResult(txID)
            if (txResult.status == FlowTransactionStatus.SEALED) {
                return txResult
            }
            Thread.sleep(1000)
        }
    }

    private fun getAccountCreatedAddress(txResult: FlowTransactionResult): FlowAddress {
        val addressHex = txResult
            .events[0]
            .event
            .value!!
            .fields[0]
            .value
            .value as String
        return FlowAddress(addressHex.substring(2))
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private fun loadScript(name: String): ByteArray
        = javaClass.classLoader.getResourceAsStream(name)!!.use { it.readAllBytes() }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun createAccount(payerAddress: FlowAddress, publicKeyHex: String): FlowAddress {

        // find payer account
        val payerAccountKey = getAccountKey(payerAddress, 0)

        // create a public key for the new account
        val newAccountPublicKey = FlowAccountKey(
            publicKey = FlowPublicKey(publicKeyHex),
            signAlgo = SignatureAlgorithm.ECDSA_P256,
            hashAlgo = HashAlgorithm.SHA3_256,
            weight = 1000
        )

        // create transaction
        var tx = FlowTransaction(
            script = FlowScript(loadScript("create_account.cdc")),
            arguments = listOf(
                FlowArgument(StringField(newAccountPublicKey.encoded.bytesToHex()))
            ),
            referenceBlockId = latestBlockID,
            gasLimit = 100,
            proposalKey = FlowTransactionProposalKey(
                address = payerAddress,
                keyIndex = payerAccountKey.id,
                sequenceNumber = payerAccountKey.sequenceNumber.toLong()
            ),
            payerAddress = payerAddress,
            authorizers = listOf(
                payerAddress
            )
        )

        // sign the transaction
        val signer = Crypto.getSigner(privateKey, payerAccountKey.hashAlgo)
        tx = tx.addEnvelopeSignature(payerAddress, payerAccountKey.id,  signer)

        // send the transaction
        val txID = accessAPI.sendTransaction(tx)

        // wait for transaction to be sealed
        val txResult = waitForSeal(txID)
        return getAccountCreatedAddress(txResult)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun transferTokens(senderAddress: FlowAddress, recipientAddress: FlowAddress, amount: BigDecimal) {
        if (amount.scale() != 8) {
            throw Exception("FLOW amount must have exactly 8 decimal places of precision (e.g. 10.00000000)")
        }
        val senderAccountKey = getAccountKey(senderAddress, 0)

        // create transaction
        var tx = FlowTransaction(
            script = FlowScript(loadScript("transfer_flow.cdc")),
            arguments = listOf(
                FlowArgument(UFix64NumberField(amount.toPlainString())),
                FlowArgument(AddressField(recipientAddress.base16Value))
            ),
            referenceBlockId = latestBlockID,
            gasLimit = 100,
            proposalKey = FlowTransactionProposalKey(
                address = senderAddress,
                keyIndex = senderAccountKey.id,
                sequenceNumber = senderAccountKey.sequenceNumber.toLong()
            ),
            payerAddress = senderAddress,
            authorizers = listOf(
                senderAddress
            )
        )

        // sign the transaction
        val signer = Crypto.getSigner(privateKey, senderAccountKey.hashAlgo)
        tx = tx.addEnvelopeSignature(senderAddress, senderAccountKey.id,  signer)

        // send the transaction
        val txID = accessAPI.sendTransaction(tx)

        // wait for transaction to be sealed
        val txResult = waitForSeal(txID)
    }
}