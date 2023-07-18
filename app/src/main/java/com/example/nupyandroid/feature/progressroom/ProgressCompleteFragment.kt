package com.example.nupyandroid.feature.progressroom

import android.content.Context
import androidx.fragment.app.activityViewModels
import com.example.nupyandroid.R
import com.example.nupyandroid.feature.progressroom.adapter.ChatRoomHistoryAdapter
import com.example.nupyandroid.base.BaseFragment
import com.example.nupyandroid.databinding.FragmentProgressCompleteBinding
import com.example.nupyandroid.feature.MainViewModel

class ProgressCompleteFragment : BaseFragment<FragmentProgressCompleteBinding>(
    R.layout.fragment_progress_complete
) {

    private val activityViewModel: MainViewModel by activityViewModels()
    lateinit var mContext: Context

    private val chatRoomHistoryAdapter by lazy {
        ChatRoomHistoryAdapter().apply {
            setOnItemClickListener(object : ChatRoomHistoryAdapter.OnItemClickListener {
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
            adapter = chatRoomHistoryAdapter
            itemAnimator = null
        }
        activityViewModel.initCompleteChat()
    }

    override fun initObserves() {
        activityViewModel.historyChat.observe(this) {
            chatRoomHistoryAdapter.submitList(it)
        }
    }

    companion object {
        fun newInstance() = ProgressCompleteFragment()
    }
}