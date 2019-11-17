package me.monotron.turingroulette.chat

import android.content.Context
import androidx.lifecycle.*
import me.monotron.turingroulette.repository.TwilioRepository
import javax.inject.Inject

class ChatViewModel @Inject constructor(
    val twilioRepository: TwilioRepository
): ViewModel(), LifecycleOwner {

    private var lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)

    var state: MutableLiveData<ChatViewState> = MutableLiveData()

    init {
        lifecycleRegistry.currentState = Lifecycle.State.CREATED
    }

    fun initialiseTwilio(applicationContext: Context) {

        twilioRepository.setApplicationContext(applicationContext)
        twilioRepository.initialiseChatRoom()

        twilioRepository.state.observeForever {
            handleRepositoryConnectionState(it)
        }
    }

    fun sendMessage(message: String) {
        twilioRepository.sendMessage(message)
    }

    private fun handleRepositoryConnectionState(state: ChatState) {
        when(state) {
            is ChatState.Connecting -> {
                this.state.value = ChatViewState.EstablishingChatSession
            }

            is ChatState.ChannelJoined -> {
                this.state.value = ChatViewState.EstablishingChatSessionSuccess
            }

            is ChatState.Error -> {
                this.state.value = ChatViewState.EstablishingChatSessionFailed
            }

            is ChatState.MessageReceived -> {
                this.state.value = ChatViewState.MessageReceived(state.message)
            }
        }
    }

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }
}