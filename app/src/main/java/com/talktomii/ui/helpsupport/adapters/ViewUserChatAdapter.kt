package com.talktomii.ui.helpsupport.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.talktomii.R
import com.talktomii.ui.helpsupport.models.UserMessageModel

class ViewUserChatAdapter(private val context: Context, list: ArrayList<UserMessageModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var list: ArrayList<UserMessageModel>

    inner class MessageInViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var messageTV: TextView

        fun bind(position: Int) {
            val messageModel: UserMessageModel = list[position]
            messageTV.setText(messageModel.message)
        }

        init {
            messageTV = itemView.findViewById(R.id.messageIn)
        }
    }

    inner class MessageOutViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        var messageTV: TextView
        fun bind(position: Int) {
            val messageModel: UserMessageModel = list[position]
            messageTV.text = messageModel.message

        }

        init {
            messageTV = itemView.findViewById(R.id.messageOut)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return if (viewType == MESSAGE_TYPE_IN) {
            MessageInViewHolder(
                LayoutInflater.from(context).inflate(R.layout.chatuser_shape, parent, false)
            )
        } else MessageOutViewHolder(
            LayoutInflater.from(context).inflate(R.layout.chat_support_shape, parent, false)
        )
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        if (list[position].msgtype === MESSAGE_TYPE_IN) {
            (holder as MessageInViewHolder).bind(position)
        } else {
            (holder as MessageOutViewHolder).bind(position)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].msgtype
    }

    companion object {
        const val MESSAGE_TYPE_IN = 1
        const val MESSAGE_TYPE_OUT = 2
    }

    init {
        this.list = list
    }

}