package me.monotron.turingroulette.chat

import com.twilio.chat.Message

open class ChatViewState {
    object EstablishingChatSession : ChatViewState()
    object EstablishingChatSessionSuccess : ChatViewState()
    object EstablishingChatSessionFailed : ChatViewState()

    object WaitingForStranger : ChatViewState()

    object StrangerHasConnected : ChatViewState()

    class MessageReceived(val message: Message) : ChatViewState()
}
