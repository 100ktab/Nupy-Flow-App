package com.example.nupyandroid.feature.chatroom.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.nupyandroid.R
import com.example.nupyandroid.databinding.ItemChatBallonBinding
import com.example.nupyandroid.model.ChatBalloon

class ChatBalloonAdapter :
    ListAdapter<ChatBalloon, ChatBalloonAdapter.ChatBalloonViewHolder>
        (ChatBalloonComparator) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatBalloonViewHolder {
        return ChatBalloonViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_chat_ballon, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ChatBalloonViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    inner class ChatBalloonViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: ItemChatBallonBinding? =
            DataBindingUtil.bind(itemView)

        @SuppressLint("UseCompatLoadingForDrawables")
        fun bind(item: ChatBalloon) {
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

object ChatBalloonComparator : DiffUtil.ItemCallback<ChatBalloon>() {
    override fun areItemsTheSame(
        oldItem: ChatBalloon,
        newItem: ChatBalloon
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: ChatBalloon,
        newItem: ChatBalloon
    ): Boolean {
        return oldItem == newItem
    }
}