package com.example.nupyandroid.feature.chatroom

import android.content.Context
import androidx.fragment.app.activityViewModels
import com.example.nupyandroid.R
import com.example.nupyandroid.base.BaseFragment
import com.example.nupyandroid.databinding.FragmentChatroomCodeBinding
import com.example.nupyandroid.feature.MainViewModel


class ChatRoomCodeFragment : BaseFragment<FragmentChatroomCodeBinding>(
    R.layout.fragment_chatroom_code
) {

    private val activityViewModel: MainViewModel by activityViewModels()
    lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun initViews() {
        initRecyclerView()
        binding.btnCancel.setOnClickListener {
            closeFragment()
        }
        binding.btnConfirm.setOnClickListener {
            activityViewModel.setFlow(MainViewModel.Flow.ChatRoom)
        }
    }

    private fun closeFragment() {
        val fragmentManager = activity?.supportFragmentManager
        fragmentManager?.let {
            it.beginTransaction().remove(this@ChatRoomCodeFragment).commit()
            it.popBackStack()
        }
    }

    companion object {
        fun newInstance() = ChatRoomCodeFragment()
    }
}