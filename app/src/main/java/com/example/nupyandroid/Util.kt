package com.example.nupyandroid

import android.content.Context
import android.content.res.Resources
import android.widget.Toast

object Util {
    const val BLOCTO_TESTNET_APP_ID = "d9fed043-5942-496e-8595-57ffe45b759c"
    const val BLOCTO_MAINNET_APP_ID = "d9fed043-5942-496e-8595-57ffe45b759c"

    const val FLOW_APP_IDENTIFIER = "Awesome App (v0.0)"
    const val FLOW_NONCE = "75f8587e5bd5f9dcc9909d0dae1f0ac5814458b2ae129620502cb936fde7120a"

    const val TESTNET_SAMPLE_CONTRACT_ADDRESS = "0x5a8143da8058740c"
    const val MAINNET_SAMPLE_CONTRACT_ADDRESS = "0x8320311d63f3b336"


    fun createToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

}