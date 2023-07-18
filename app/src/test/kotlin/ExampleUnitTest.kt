//package com.example.nupyandroid
//
//import com.google.common.io.BaseEncoding
//import com.nftco.flow.sdk.Flow
//import com.nftco.flow.sdk.FlowAccessApi
//import com.nftco.flow.sdk.FlowAddress
//import com.nftco.flow.sdk.HashAlgorithm
//import com.nftco.flow.sdk.SignatureAlgorithm
//import com.nftco.flow.sdk.Signer
//import com.nftco.flow.sdk.crypto.Crypto
//import com.nftco.flow.sdk.simpleFlowTransaction
//import org.junit.Assert.assertEquals
//import org.junit.Test
//import java.math.BigDecimal
//
///**
// * Example local unit test, which will execute on the development machine (host).
// *
// * See [testing documentation](http://d.android.com/tools/testing).
// */
//
//val serviceAccountAddress: FlowAddress = FlowAddress("f8d6e0586b0a20c7")
//
//class ExampleUnitTest {
//    @Test
//    fun addition_isCorrect() {
//        assertEquals(4, 2 + 2)
//    }
//
//    val address = "0x6e1d1217a98b542c"
//
//    @Test
//    fun getAccount() {
//        val accessAPI = TestUtils.newTestnetAccessApi()
//        val account = accessAPI.getAccountAtLatestBlock(FlowAddress(address))!!
////        val account3 = account.contracts.mapValues { (_, value) ->
////            value.bytes.joinToString("") { byte ->
////                String.format("%02X", byte)
////            }
////        }
//
////        print( account3)
//    }
//
//
//    @Test
//    fun getAccountBalance() {
//        val accessAPI = TestUtils.newTestnetAccessApi()
//        val account = accessAPI.getAccountAtLatestBlock(FlowAddress(address))!!
//
//        val id = accessAPI.getLatestBlock().id
//        print("id")
//        print(id.bytes.joinToString("") { byte ->
//            String.format("%02X", byte)
//        })
//
//        print("\naccount.balance\n")
//        print(account.balance)
//        print("\naccount.keys.size\n")
//        print(account.keys.size)
//        print("\n\n")
//        print(account.keys[0].publicKey.bytes.joinToString("") { byte ->
//            String.format("%02X", byte)
//        })
//        print("\n\n")
//
//        val publicKey =
//            "60f6942704598062b4fae649157b083de64c96db88da35b22b0fa03fb009bf3338dd3cce4b32043b3bbb97dd25f05c1e62ba2497620a619a9c3744fb4f1fa52f60F6942704598062B4FAE649157B083DE64C96DB88DA35B22B0FA03FB009BF3338DD3CCE4B32043B3BBB97DD25F05C1E62BA2497620A619A9C3744FB4F1FA52F"
//        val privateKey = "15f804fe02e7166a6503ced40c6c6fa1a5c341c50e1f6a3612ac0996d35095a4"
//        val signAlgo = SignatureAlgorithm.ECDSA_P256
//        val hashAlgo = HashAlgorithm.SHA3_256
//
//        var serviceAccount: TestAccount = TestAccount(
//            address = "0x6e1d1217a98b542c",
//            privateKey = privateKey,
//            publicKey = publicKey,
//            signAlgo = signAlgo,
//            hashAlgo = hashAlgo,
//            keyIndex = 0,
//            balance = BigDecimal(-1)
//        )
//
//
//        val result = accessAPI.simpleFlowTransaction(
//            serviceAccount.flowAddress,
//            serviceAccount.signer
//        ) {
//            script {
//                """
//                     import NUPY from 0x6e1d1217a98b542c
//
//                    // Print the NFTs owned by account
//                    pub fun main(address: Address) : [UInt64] {
//                      // Get the public account object for account 0x01
//                      let nftOwner = getAccount(address)
//
//                      // Find the public Receiver capability for their Collection
//                      let capability = nftOwner.getCapability<&{NUPY.NFTReceiver}>(NUPY.CollectionPublicPath)
//
//                      // borrow a reference from the capability
//                      let receiverRef = capability.borrow()
//                              ?? panic("Could not borrow receiver reference")
//
//                      // Log the NFTs that they own as an array of IDs
//                      log("Account 1 NFTs")
//                      log(receiverRef.getIDs())
//                      return receiverRef.getIDs()
//                  }
//                """.trimIndent()
//            }
//            arguments {
//                arg { string(("0x6e1d1217a98b542c")) }
//            }
//        }.sendAndWaitForSeal()
//            .throwOnError()
//
//
//        print(result)
//    }
////        accessAPI.simpleFlowTransaction(
////            serviceAcc
////        )
//
//
////    @Test
////    fun `Test something on the emnulator`() {
//////        lateinit var accessAPI: FlowAccessApi
////
////        val accessAPI = TestUtils.newMainnetAccessApi()
////        lateinit var serviceAccount: TestAccount
////
////        val newAccountKeyPair = Crypto.generateKeyPair(SignatureAlgorithm.ECDSA_P256)
////        val newAccountPublicKey = FlowAccountKey(
////            publicKey = FlowPublicKey(newAccountKeyPair.public.hex),
////            signAlgo = SignatureAlgorithm.ECDSA_P256,
////            hashAlgo = HashAlgorithm.SHA3_256,
////            weight = 1000
////        )
////
////        val result = accessAPI.simpleFlowTransaction(
////            serviceAccount.flowAddress,
////            serviceAccount.signer
////        ) {
////            script {
////                """
////                    transaction(publicKey: String) {
////                        prepare(signer: AuthAccount) {
////                            let account = AuthAccount(payer: signer)
////                            account.addPublicKey(publicKey.decodeHex())
////                        }
////                    }
////                """
////            }
////            arguments {
////                arg { string(newAccountPublicKey.encoded.bytesToHex()) }
////            }
////        }.sendAndWaitForSeal()
////            .throwOnError()
//////        assertThat(result.status).isEqualTo(FlowTransactionStatus.SEALED)
////    }
//
//}
//
//fun ByteArray.bytesToHex(): String = BaseEncoding.base16().lowerCase().encode(this)
//
//data class TestAccount(
//    val address: String = "0x6e1d1217a98b542c",
//    val privateKey: String,
//    val publicKey: String,
//    val signAlgo: SignatureAlgorithm,
//    val hashAlgo: HashAlgorithm,
//    val keyIndex: Int,
//    val balance: BigDecimal
//) {
//    val signer: Signer
//        get() = Crypto.getSigner(
//            privateKey = Crypto.decodePrivateKey(privateKey, signAlgo),
//            hashAlgo = hashAlgo
//        )
//
//    val flowAddress: FlowAddress get() = FlowAddress(address)
//
//    val isValid: Boolean
//        get() = !address.isEmpty()
//            && !privateKey.isEmpty()
//            && !publicKey.isEmpty()
//            && signAlgo != SignatureAlgorithm.UNKNOWN
//            && hashAlgo != HashAlgorithm.UNKNOWN
//            && keyIndex >= 0
//}
//
//
//object TestUtils {
//
//    fun newMainnetAccessApi(): FlowAccessApi = Flow.newAccessApi(MAINNET_HOSTNAME)
//
//    fun newTestnetAccessApi(): FlowAccessApi = Flow.newAccessApi(TESTNET_HOSTNAME)
//
//    val MAINNET_HOSTNAME = "access.mainnet.nodes.onflow.org"
//    val TESTNET_HOSTNAME = "access.devnet.nodes.onflow.org"
//}
//
//data class FlowNFT(
//    val id: String,
//    val owner: String,
//    val metadata: Map<String, Any>
//)