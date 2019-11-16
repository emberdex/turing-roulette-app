package me.monotron.turingroulette.chat

open class ChatViewState {
    object EstablishingChatSession : ChatViewState()
    object EstablishingChatSessionSuccess : ChatViewState()
    object EstablishingChatSessionFailed : ChatViewState()

    object WaitingForStranger : ChatViewState()

    object StrangerHasConnected : ChatViewState()
}
