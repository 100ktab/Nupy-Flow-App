package com.example.nupyandroid.feature.chatroom

import android.content.Context
import androidx.fragment.app.activityViewModels
import com.example.nupyandroid.DesignUtil.setCircleImageUrl
import com.example.nupyandroid.R
import com.example.nupyandroid.feature.chatroom.adapter.ChatBalloonAdapter
import com.example.nupyandroid.base.BaseFragment
import com.example.nupyandroid.databinding.FragmentChatroomBinding
import com.example.nupyandroid.feature.MainViewModel
import com.example.nupyandroid.model.ChatBalloon

class ChatRoomFragment : BaseFragment<FragmentChatroomBinding>(
    R.layout.fragment_chatroom
) {

    private val activityViewModel: MainViewModel by activityViewModels()
    lateinit var mContext: Context

    private val waitList = mutableListOf<ChatBalloon>(
        ChatBalloon(
            isNupy = true,
            content = "Welcome to Harmonic Parramatta 2023!",
            content2 = "Can you share your location? I'll airdrop you a visitor verification NFT!",
        ),
        ChatBalloon(
            isNupy = true,
            content = "I'm NUPY! I'm here to deliver the event.",
        ),
        ChatBalloon(
            isNupy = true,
            content = "Thank you! I've airdrop to your wallet!",
        ),
    )
    private val chatAdapter by lazy {
        ChatBalloonAdapter().apply {
            setOnItemClickListener(object : ChatBalloonAdapter.OnItemClickListener {
                override fun onItemClick(id: String) {

                }
            })
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }


    override fun initViews() {
        binding.btnCancel.setOnClickListener {
            closeFragment()
        }
        binding.btnSend.setOnClickListener {
            activityViewModel.addChatBalloon(
                ChatBalloon(
                    isNupy = false,
                    content = binding.etMsg.text.toString(),
                )
            )
            activityViewModel.addChatBalloon(waitList.first())
            waitList.removeAt(0)
            binding.etMsg.text?.clear()
        }
        initRoomDetail()
    }

    private fun initRoomDetail() {
        val item = activityViewModel.historyChat.value?.first()
        binding.ivChatImage.setCircleImageUrl(item?.icon)
        binding.tvChat.text = item?.title
        activityViewModel.addChatBalloon(waitList.firstOrNull())
        waitList.removeAt(0)
    }


    override fun initRecyclerView() {
        binding.rvChat.apply {
            adapter = chatAdapter
            itemAnimator = null
        }

    }

    override fun initObserves() {
        activityViewModel.chatBalloon.observe(this) {
            chatAdapter.submitList(it)
        }
    }


    private fun closeFragment() {
        val fragmentManager = activity?.supportFragmentManager
        fragmentManager?.let {
            it.beginTransaction().remove(this@ChatRoomFragment).commit()
            it.popBackStack()
        }
    }

    companion object {
        fun newInstance() = ChatRoomFragment()
    }
}