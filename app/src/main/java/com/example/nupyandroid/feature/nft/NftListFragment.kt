package com.example.nupyandroid.feature.nft

import androidx.fragment.app.activityViewModels
import com.example.nupyandroid.R
import com.example.nupyandroid.feature.nft.adapter.NftAdapter
import com.example.nupyandroid.base.BaseFragment
import com.example.nupyandroid.databinding.FragmentNftListBinding
import com.example.nupyandroid.feature.MainViewModel

class NftListFragment : BaseFragment<FragmentNftListBinding>(
    R.layout.fragment_nft_list
) {

    private val activityViewModel: MainViewModel by activityViewModels()
    private val nftAdapter by lazy {
        NftAdapter().apply {
            setOnItemClickListener(object : NftAdapter.OnItemClickListener {
                override fun onItemClick(id: Int) {
                    activityViewModel.setFlow(MainViewModel.Flow.NftDetail)
                    activityViewModel.nftDetailId.value = id

                }
            })
        }
    }

    override fun initViews() {
        binding.btnCancel.setOnClickListener {
            closeFragment()
        }
    }

    override fun initRecyclerView() {
        binding.rvNft.apply {
            adapter = nftAdapter
            itemAnimator = null
        }
        activityViewModel.initNft()
    }

    private fun closeFragment() {
        val fragmentManager = activity?.supportFragmentManager
        fragmentManager?.let {
            it.beginTransaction().remove(this@NftListFragment).commit()
            it.popBackStack()
        }
    }

    override fun initObserves() {
        activityViewModel.nftList.observe(this) {
            nftAdapter.submitList(it)
        }
    }

    companion object {
        fun newInstance() = NftListFragment()
    }
}