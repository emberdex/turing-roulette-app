package me.monotron.turingroulette.startup

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import me.monotron.turingroulette.repository.TuringRepository
import retrofit2.Response
import javax.inject.Inject

class StartupViewModel @Inject constructor(val turingRepository: TuringRepository) : ViewModel(), LifecycleOwner {

    var state: MutableLiveData<StartupViewState> = MutableLiveData()

    private var lifecycleRegistry: LifecycleRegistry = LifecycleRegistry(this)

    init {
        lifecycleRegistry.currentState = Lifecycle.State.CREATED
    }

    fun handleStartChattingButtonClicked(): LiveData<Boolean> {

        state.value = StartupViewState.ServiceHealthCheck

        return liveData(Dispatchers.IO) {
            val returned = turingRepository.performHealthCheck()

            emit(returned.isSuccessful)
        }
    }

    override fun getLifecycle(): Lifecycle {
        return lifecycleRegistry
    }
}
