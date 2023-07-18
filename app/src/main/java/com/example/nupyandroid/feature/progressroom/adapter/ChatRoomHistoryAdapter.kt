package com.example.nupyandroid.feature.progressroom.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nupyandroid.R
import com.example.nupyandroid.databinding.ItemChatHistoryBinding
import com.example.nupyandroid.model.ChatHistory

class ChatRoomHistoryAdapter :
    ListAdapter<ChatHistory, ChatRoomHistoryAdapter.ChatRoomHistoryViewHolder>
        (ChatRoomHistoryComparator) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatRoomHistoryViewHolder {
        return ChatRoomHistoryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_chat_history, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ChatRoomHistoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class ChatRoomHistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: ItemChatHistoryBinding? =
            DataBindingUtil.bind(itemView)

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(item: ChatHistory) {
            binding?.model = item

//            binding?.reviewWrite?.setOnClickListener {
//                mListener?.onItemClick(item.name)
//            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(id: String)
    }

    private var mListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        mListener = listener
    }
}

object ChatRoomHistoryComparator : DiffUtil.ItemCallback<ChatHistory>() {
    override fun areItemsTheSame(
        oldItem: ChatHistory,
        newItem: ChatHistory
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: ChatHistory,
        newItem: ChatHistory
    ): Boolean {
        return oldItem == newItem
    }
}