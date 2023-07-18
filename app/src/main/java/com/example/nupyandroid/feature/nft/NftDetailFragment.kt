package com.example.nupyandroid.feature.nft

import android.content.Context
import androidx.fragment.app.activityViewModels
import com.example.nupyandroid.DesignUtil.setImageUrl300
import com.example.nupyandroid.R
import com.example.nupyandroid.base.BaseFragment
import com.example.nupyandroid.databinding.FragmentNftDetailBinding
import com.example.nupyandroid.feature.MainViewModel


class NftDetailFragment : BaseFragment<FragmentNftDetailBinding>(
    R.layout.fragment_nft_detail
) {

    private val activityViewModel: MainViewModel by activityViewModels()
    lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun initViews() {
        binding.btnCancel.setOnClickListener {
            activityViewModel.setFlow(MainViewModel.Flow.NftList)
        }
        initNftDetail()
    }

    private fun initNftDetail() {
        val item = activityViewModel.nftList.value?.first {
            it.id == activityViewModel.nftDetailId.value
        }
        binding.nftImageView.setImageUrl300(item?.image)
        binding.tvTitle.text = item?.title
        binding.tvContent.text = item?.description
    }

    companion object {
        fun newInstance() = NftDetailFragment()
    }
}