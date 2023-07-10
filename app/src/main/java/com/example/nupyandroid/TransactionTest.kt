//package com.example.nupyandroid
//
//import com.nftco.flow.sdk.FlowAccessApi
//import com.nftco.flow.sdk.FlowTransactionStatus
//
//
//@FlowEmulatorTest
//class TransactionTest {
//
//    @FlowTestClient
//    lateinit var accessAPI: FlowAccessApi
//
//    @FlowServiceAccountCredentials
//    lateinit var serviceAccount: TestAccount
//
//    @Test
//    fun `Test something on the emnulator`() {
//        val result = accessAPI.simpleFlowTransaction(
//            serviceAccount.flowAddress,
//            serviceAccount.signer
//        ) {
//            script {
//                """
//                    transaction(publicKey: String) {
//                        prepare(signer: AuthAccount) {
//                            let account = AuthAccount(payer: signer)
//                            account.addPublicKey(publicKey.decodeHex())
//                        }
//                    }
//                """
//            }
//            arguments {
//                arg { string(newAccountPublicKey.encoded.bytesToHex()) }
//            }
//        }.sendAndWaitForSeal()
//            .throwOnError()
//        assertThat(result.status).isEqualTo(FlowTransactionStatus.SEALED)
//    }
//
//}