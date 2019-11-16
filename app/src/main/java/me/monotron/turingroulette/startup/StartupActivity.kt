package me.monotron.turingroulette.startup

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.INVISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.observe
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import me.monotron.turingroulette.R
import me.monotron.turingroulette.base.BaseActivity
import me.monotron.turingroulette.chat.ChatActivity
import javax.inject.Inject

class StartupActivity : BaseActivity() {

    @Inject lateinit var viewModel: StartupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)

        viewModel.state.observe(this, Observer { state ->
            onViewStateChanged(state)
        })

        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()

        setHealthCheckSpinnerVisibility(INVISIBLE)

        startup_start_chatting_button.setOnClickListener {
            viewModel.handleStartChattingButtonClicked().observe(this, Observer {
                if (it) {
                    viewModel.state.value = StartupViewState.ServiceHealthCheckSucceeded
                } else {
                    viewModel.state.value = StartupViewState.ServiceHealthCheckFailed

                }
            })
        }
    }

    private fun setHealthCheckSpinnerVisibility(visibility: Int) {
        startup_health_check_spinner.visibility = visibility
    }

    private fun showHealthCheckFailedDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.network_error_dialog_title)
            .setMessage(R.string.network_error_dialog_text)
            .setPositiveButton(R.string.dialog_ok_button) { dialog, _ ->
                dialog.dismiss()
            }
            .create().show()
    }

    private fun showChatActivity() {

        val intent = Intent(this, ChatActivity::class.java)
        startActivity(intent)
    }

    private fun onViewStateChanged(newState: StartupViewState) {
        when (newState) {
            is StartupViewState.ServiceHealthCheck -> {
                setHealthCheckSpinnerVisibility(View.VISIBLE)
            }

            is StartupViewState.ServiceHealthCheckSucceeded -> {
                setHealthCheckSpinnerVisibility(INVISIBLE)
                showChatActivity()
            }

            is StartupViewState.ServiceHealthCheckFailed -> {
                setHealthCheckSpinnerVisibility(INVISIBLE)
                showHealthCheckFailedDialog()
            }
        }
    }
}
