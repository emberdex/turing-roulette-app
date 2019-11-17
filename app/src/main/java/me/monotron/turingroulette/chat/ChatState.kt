package me.monotron.turingroulette.chat

import com.twilio.chat.Message

open class ChatState {

    object Connecting : ChatState()
    object ChannelJoined : ChatState()
    object Error : ChatState()

    class MessageReceived(val message: Message) : ChatState()
}