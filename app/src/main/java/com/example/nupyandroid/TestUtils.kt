package com.example.nupyandroid

import com.nftco.flow.sdk.Flow
import com.nftco.flow.sdk.FlowAccessApi


object TestUtils {

    fun newMainnetAccessApi(): FlowAccessApi = Flow.newAccessApi(MAINNET_HOSTNAME)

    fun newTestnetAccessApi(): FlowAccessApi = Flow.newAccessApi(TESTNET_HOSTNAME)

    val MAINNET_HOSTNAME = "access.mainnet.nodes.onflow.org"
    val TESTNET_HOSTNAME = "access.devnet.nodes.onflow.org"
}