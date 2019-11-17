package me.monotron.turingroulette.chat

enum class MessageType { SENT, RECEIVED }
data class UserMessage(val type: MessageType, val body: String)