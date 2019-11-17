package me.monotron.turingroulette.chat.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_received_message.view.*

class SentMessageHolder constructor(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bind(message: String) {
        itemView.message_body.text = message
    }
}