package com.example.nupyandroid.feature.nft.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nupyandroid.R
import com.example.nupyandroid.databinding.ItemNftRowBinding
import com.example.nupyandroid.model.Nft

class NftAdapter :
    ListAdapter<Nft, NftAdapter.NftViewHolder>
        (NftComparator) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NftViewHolder {
        return NftViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_nft_row, parent, false)
        )
    }

    override fun onBindViewHolder(holder: NftViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class NftViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: ItemNftRowBinding? =
            DataBindingUtil.bind(itemView)

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(item: Nft) {
            binding?.name = item.title
            binding?.image = item.image

            binding?.layoutBastItem?.setOnClickListener {
                mListener?.onItemClick(item.id)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(id: Int)
    }

    private var mListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        mListener = listener
    }
}

object NftComparator : DiffUtil.ItemCallback<Nft>() {
    override fun areItemsTheSame(
        oldItem: Nft,
        newItem: Nft
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Nft,
        newItem: Nft
    ): Boolean {
        return oldItem == newItem
    }
}