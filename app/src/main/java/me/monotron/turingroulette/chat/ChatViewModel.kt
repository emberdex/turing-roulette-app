package me.monotron.turingroulette.chat

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.monotron.turingroulette.repository.TwilioRepository
import javax.inject.Inject

class ChatViewModel @Inject constructor(
    val twilioRepository: TwilioRepository
    // TODO: inject the Twilio Chat SDK components as required
): ViewModel() {

    var state: MutableLiveData<ChatViewState> = MutableLiveData()

    fun initialiseTwilio(applicationContext: Context) {

        twilioRepository.setApplicationContext(applicationContext)
        twilioRepository.initialiseChatRoom()
    }
}