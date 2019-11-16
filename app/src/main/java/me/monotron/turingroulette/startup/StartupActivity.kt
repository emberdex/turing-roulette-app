package me.monotron.turingroulette.startup

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import me.monotron.turingroulette.R

class StartupActivity : AppCompatActivity() {

    private lateinit var viewModel: StartupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(StartupViewModel::class.java)

        viewModel.state.observe(this, Observer { state ->
            onViewStateChanged(state)
        })

        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        setHealthCheckSpinnerVisibility(View.INVISIBLE)
    }

    fun setHealthCheckSpinnerVisibility(visibility: Int) {
        startup_health_check_spinner.visibility = visibility
    }

    private fun onViewStateChanged(newState: StartupViewState) {
        when (newState) {
            is StartupViewState.ServiceHealthCheck -> {
                setHealthCheckSpinnerVisibility(View.VISIBLE)
            }
        }
    }
}
