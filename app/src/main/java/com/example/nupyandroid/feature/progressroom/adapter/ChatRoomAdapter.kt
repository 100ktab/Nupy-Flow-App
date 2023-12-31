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
import com.example.nupyandroid.databinding.ItemChatRoomBinding
import com.example.nupyandroid.model.Chat

class ChatRoomAdapter : ListAdapter<Chat, ChatRoomAdapter.ChatViewHolder>
    (ChatComparator) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_chat_room, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class ChatViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: ItemChatRoomBinding? =
            DataBindingUtil.bind(itemView)

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(item: Chat) {
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

object ChatComparator : DiffUtil.ItemCallback<Chat>() {
    override fun areItemsTheSame(
        oldItem: Chat,
        newItem: Chat
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Chat,
        newItem: Chat
    ): Boolean {
        return oldItem == newItem
    }
}