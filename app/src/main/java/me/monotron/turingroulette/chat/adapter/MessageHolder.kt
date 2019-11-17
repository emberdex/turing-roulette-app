package me.monotron.turingroulette.chat.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_received_message.view.*
import me.monotron.turingroulette.chat.UserMessage

class MessageHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(message: UserMessage) {
        itemView.message_body.text = message.body
    }
}