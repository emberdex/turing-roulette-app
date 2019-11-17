package me.monotron.turingroulette.chat.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import me.monotron.turingroulette.R
import me.monotron.turingroulette.chat.MessageType
import me.monotron.turingroulette.chat.UserMessage

class MessageListAdapter(private var context: Context, var messageList: ArrayList<UserMessage>) :
    RecyclerView.Adapter<MessageHolder>() {

    override fun getItemCount(): Int {
        return messageList.size
    }

    override fun getItemViewType(position: Int): Int {
        return messageList[position].type.ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageHolder {
        val view: View = if(viewType == MessageType.RECEIVED.ordinal) {
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_received_message, parent, false)
        } else {
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_sent_message, parent, false)
        }

        return MessageHolder(view)
    }

    override fun onBindViewHolder(holder: MessageHolder, position: Int) {
        val message: UserMessage = messageList[position]

        holder.bind(message)
    }
}