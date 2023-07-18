package com.example.nupyandroid.feature

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.nupyandroid.Util.BLOCTO_MAINNET_APP_ID
import com.example.nupyandroid.Util.BLOCTO_TESTNET_APP_ID
import com.example.nupyandroid.Util.FLOW_APP_IDENTIFIER
import com.example.nupyandroid.Util.FLOW_NONCE
import com.example.nupyandroid.base.BaseViewModel
import com.example.nupyandroid.model.Chat
import com.example.nupyandroid.model.ChatBalloon
import com.example.nupyandroid.model.ChatHistory
import com.example.nupyandroid.model.Nft
import com.portto.fcl.Fcl
import com.portto.fcl.model.CompositeSignature
import com.portto.fcl.model.Result
import com.portto.fcl.model.authn.AccountProofResolvedData
import com.portto.fcl.model.authn.AppConfig
import com.portto.fcl.model.authn.Config
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class MainViewModel : BaseViewModel() {

    //    val address = "0x6e1d1217a98b542c"
    val balance = MutableLiveData<String>()
    val NftCount = MutableLiveData<String>("3 NFT")

    val flow = MutableLiveData<Flow>()

    enum class Flow { ChatRoom, ChatCode, NftList, NftDetail, Login, LoginComplete }

    fun setFlow(_flow: Flow) {
        flow.value = _flow
    }

    private val _progressChat = MutableLiveData<List<Chat>>()
    val progressChat: LiveData<List<Chat>> get() = _progressChat

    private val _historyChat = MutableLiveData<List<ChatHistory>>()
    val historyChat: LiveData<List<ChatHistory>> get() = _historyChat

    private val _chatBalloon = MutableLiveData<List<ChatBalloon>>()
    val chatBalloon: LiveData<List<ChatBalloon>> get() = _chatBalloon

    private val _nftList = MutableLiveData<List<Nft>>()
    val nftList: LiveData<List<Nft>> get() = _nftList

    val nftDetailId = MutableLiveData<Int>()

    init {
//        val accessAPI = TestUtils.newTestnetAccessApi()
//        val account = accessAPI.getAccountAtLatestBlock(FlowAddress(address))!!
//        balance.value = "FLOW "+account.balance.toString()
        balance.value = "FLOW " + 1000.10
    }

    // current network
    private val _isCurrentMainnet = MutableLiveData(false)
    val isCurrentMainnet: LiveData<Boolean> get() = _isCurrentMainnet

    // authn - account address
    private val _address = MutableLiveData<String?>(null)
    val address: LiveData<String?> get() = _address

    // authn - signatures
    private val _accountProofSignatures = MutableLiveData<List<CompositeSignature>?>(null)
    val accountProofSignatures: LiveData<List<CompositeSignature>?> get() = _accountProofSignatures

    // Message to be shown as snackbar
    private val _message = MutableStateFlow<String?>(null)
    val message get() = _message.asStateFlow()

    fun connect(withAccountPoof: Boolean) = viewModelScope.launch {
        val config = Config(
            app = AppConfig(
                id = if (isCurrentMainnet.value == true) BLOCTO_MAINNET_APP_ID
                else BLOCTO_TESTNET_APP_ID
            )
        )
        val accountProofResolvedData =
            if (withAccountPoof) AccountProofResolvedData(FLOW_APP_IDENTIFIER, FLOW_NONCE, config)
            else AccountProofResolvedData(config = config)
        when (val result = Fcl.authenticate(accountProofResolvedData)) {
            is Result.Success -> {
                _address.value = result.value
                _accountProofSignatures.value = Fcl.currentUser?.accountProofData?.signatures
                setFlow(Flow.LoginComplete)
            }

            is Result.Failure -> _message.value = result.throwable.message
        }
    }

    fun addChatBalloon(item: ChatBalloon?) {
        item?.let {
            _chatBalloon.value = (_chatBalloon.value?.toList() ?: listOf()).plus(
                ChatBalloon(
                    id = _chatBalloon.value?.size ?: 0,
                    isNupy = it.isNupy,
                    content = it.content,
                    content2 = it.content2,
                    image = it.image,
                    icon = historyChat.value?.first()?.icon,
                )
            )
        }
    }


    fun initNft() {
        _nftList.value = listOf(
            Nft(
                id = 1,
                image = "https://raw.githubusercontent.com/Looktab-naer/imgs/main/flow/nft1.png",
                title = "Nuffy Hackathon Event",
                description = "Nuffy  test description"
            ), Nft(
                id = 2,
                image = "https://raw.githubusercontent.com/Looktab-naer/imgs/main/flow/nft2.png",
                title = "Look Tab Open Event",
                description = "Nuffy  test description"
            ), Nft(
                id = 3,
                image = "https://bafybeicygehs2gweduqubzj4vdrbf3n5llc42gd2az44fqdexxlzmuzwzy.ipfs.w3s.link/0x605a0f3df4dcdfda-1689618940129",
                title = "Harmonic Parramatta 2023",
                description = "The 'Harmonic Parramatta 2023' is a one-of-a-kind digital asset that verifies your attendance at the music concert held at the iconic Westfield in Parramatta, Australia. This Non-Fungible Token (NFT) is a permanent record of your participation in this unique event, allowing you to share your love of music and art with the world. This token combines digital artwork, music, and metadata, all securely stored on the blockchain, to provide a lasting testament to your involvement in this momentous occasion."
            )
        )
        NftCount.value = "${nftList.value?.size ?: 0} NFT"
    }

    fun initProgressChat() {
        _progressChat.value = listOf(
            Chat(
                id = 0,
                title = "NUPY Event",
                icon = "https://raw.githubusercontent.com/Looktab-naer/imgs/main/flow/profile1.png",
                description = "Registration completed. View the following information",
                timeline = "9:54 PM",
            ),
            Chat(
                id = 1,
                title = "Mega Mutant Serum Event",
                icon = "https://raw.githubusercontent.com/Looktab-naer/imgs/main/flow/profile2.png",
                description = "Hello At Mega Mutant serum Event",
                timeline = "12:54 AM",
            ),
            Chat(
                id = 2,
                title = "InstaStar",
                icon = "https://raw.githubusercontent.com/Looktab-naer/imgs/main/flow/profile3.png",
                description = "In order to inform you of the benefits of community membership, we are sending you DM.",
                timeline = "2:00 PM",
            ),
            Chat(
                id = 3,
                title = "CryptoPunk Event",
                icon = "https://raw.githubusercontent.com/Looktab-naer/imgs/main/flow/profile4.png",
                description = "2023 Iddenver visitors' NFT airdrops can be obtained by sharing their location.",
                timeline = "11:34 AM",
            ),
            Chat(
                id = 4,
                title = "Doge Event",
                icon = "https://raw.githubusercontent.com/Looktab-naer/imgs/main/flow/profile5.png",
                description = "Want a cute NFT? That's Doge",
                timeline = "12:54 AM",
            ),
            Chat(
                id = 5,
                title = "Choco",
                icon = "https://raw.githubusercontent.com/Looktab-naer/imgs/main/flow/profile6.png",
                description = "NFT airdrop for 2023 visitors, share your location to receive an airdrop.",
                timeline = "9:09 AM",
            ),

            )
    }

    fun initCompleteChat() {
        _historyChat.value = listOf(
            ChatHistory(
                id = 1,
                title = "Harmonic Parramatta 2023",
                icon = "https://img.freepik.com/free-vector/flat-design-musical-instruments_23-2149533579.jpg",
                description = "The 'Harmonic Parramatta 2023' is a one-of-a-kind digital asset that verifies your attendance at the music concert held at the iconic Westfield in Parra",
                timeline = "8:00 AM",
                complete = true
            ),
            ChatHistory(
                id = 2,
                title = "InstaStar",
                icon = "https://raw.githubusercontent.com/Looktab-naer/imgs/main/flow/profile3.png",
                description = "In order to inform you of the benefits of community membership, we are sending you DM.",
                timeline = "2:00 PM",
                complete = true
            ),
            ChatHistory(
                id = 3,
                title = "CryptoPunk Event",
                icon = "https://raw.githubusercontent.com/Looktab-naer/imgs/main/flow/profile4.png",
                description = "2023 Iddenver visitors' NFT airdrops can be obtained by sharing their location.",
                timeline = "11:34 AM",
                complete = true
            ),
            ChatHistory(
                id = 4,
                title = "Doge Event",
                icon = "https://raw.githubusercontent.com/Looktab-naer/imgs/main/flow/profile5.png",
                description = "Want a cute NFT? That's Doge",
                timeline = "12:54 AM",
                complete = false
            ),
            ChatHistory(
                id = 5,
                title = "Choco",
                icon = "https://raw.githubusercontent.com/Looktab-naer/imgs/main/flow/profile6.png",
                description = "NFT airdrop for 2023 visitors, share your location to receive an airdrop.",
                timeline = "9:09 AM",
                complete = false
            ),
        )

    }

}