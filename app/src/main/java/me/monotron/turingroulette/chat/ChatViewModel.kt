package me.monotron.turingroulette.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import me.monotron.turingroulette.repository.TuringRepository
import javax.inject.Inject

class ChatViewModel @Inject constructor(
    val turingRepository: TuringRepository
    // TODO: inject the Twilio Chat SDK components as required
): ViewModel() {

    var state: MutableLiveData<ChatViewState> = MutableLiveData()
}