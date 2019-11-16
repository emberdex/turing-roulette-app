package me.monotron.turingroulette.startup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StartupViewModel : ViewModel() {

    var state: MutableLiveData<StartupViewState> = MutableLiveData()

    // TODO: Implement health check service

    fun handleStartChattingButtonClicked() {

        state.value = StartupViewState.ServiceHealthCheck

        // TODO: do the actual health check, dipshit

        
    }
}