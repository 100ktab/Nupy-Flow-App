package com.example.nupyandroid.feature.progressroom

import android.content.Context
import androidx.fragment.app.activityViewModels
import com.example.nupyandroid.R
import com.example.nupyandroid.feature.progressroom.adapter.ChatRoomAdapter
import com.example.nupyandroid.base.BaseFragment
import com.example.nupyandroid.databinding.FragmentProgressIngBinding
import com.example.nupyandroid.feature.MainViewModel

class ProgressIngFragment : BaseFragment<FragmentProgressIngBinding>(
    R.layout.fragment_progress_ing
) {

    private val activityViewModel: MainViewModel by activityViewModels()
    lateinit var mContext: Context

    private val chatRoomAdapter by lazy {
        ChatRoomAdapter().apply {
            setOnItemClickListener(object : ChatRoomAdapter.OnItemClickListener {
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
    }

    override fun initRecyclerView() {
        binding.rvChat.apply {
            adapter = chatRoomAdapter
            itemAnimator = null
        }
        activityViewModel.initProgressChat()
    }

    override fun initObserves() {
        activityViewModel.progressChat.observe(this) {
            chatRoomAdapter.submitList(it)
        }
    }


    companion object {
        fun newInstance() = ProgressIngFragment()
    }
}