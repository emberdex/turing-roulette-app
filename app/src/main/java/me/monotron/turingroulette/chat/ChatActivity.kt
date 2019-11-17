package me.monotron.turingroulette.chat

import android.app.AlertDialog
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_chat.*
import me.monotron.turingroulette.R
import me.monotron.turingroulette.base.BaseActivity
import me.monotron.turingroulette.chat.MessageType.RECEIVED
import me.monotron.turingroulette.chat.MessageType.SENT
import me.monotron.turingroulette.chat.adapter.MessageListAdapter
import javax.inject.Inject

class ChatActivity : BaseActivity() {

    @Inject lateinit var viewModel: ChatViewModel

    var snackbar: Snackbar? = null
    lateinit var messageRecycler: RecyclerView
    lateinit var messageListAdapter: MessageListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)

        super.onCreate(savedInstanceState)

        viewModel.state.observe(this, Observer {
            onViewStateChanged(it)
        })

        setContentView(R.layout.activity_chat)

        // TODO: remove
        val messages: List<UserMessage> = listOf(
            UserMessage(SENT, "Hello!"),
            UserMessage(RECEIVED, "OK boomer"),
            UserMessage(RECEIVED, "This is a very quick test of how it handles long paragraphs This is a very quick test of how it handles long paragraphs This is a very quick test of how it handles long paragraphs This is a very quick test of how it handles long paragraphs This is a very quick test of how it handles long paragraphs This is a very quick test of how it handles long paragraphs This is a very quick test of how it handles long paragraphs This is a very quick test of how it handles long paragraphs This is a very quick test of how it handles long paragraphs This is a very quick test of how it handles long paragraphs This is a very quick test of how it handles long paragraphs This is a very quick test of how it handles long paragraphs This is a very quick test of how it handles long paragraphs This is a very quick test of how it handles long paragraphs This is a very quick test of how it handles long paragraphs This is a very quick test of how it handles long paragraphs")
        )

        messageRecycler = message_recycler_view as RecyclerView
        messageListAdapter = MessageListAdapter(this, messages)
        messageRecycler.layoutManager = LinearLayoutManager(this)
        messageRecycler.adapter = messageListAdapter
    }

    override fun onResume() {

        super.onResume()

        viewModel.initialiseTwilio(applicationContext)
    }

    private fun onViewStateChanged(state: ChatViewState) {
        when (state) {
            is ChatViewState.EstablishingChatSession -> {
                snackbar = Snackbar.make(root_chat_layout, R.string.chat_connecting_to_server, Snackbar.LENGTH_INDEFINITE)
                snackbar?.show()
            }

            is ChatViewState.EstablishingChatSessionFailed -> {
                snackbar?.dismiss()

                AlertDialog.Builder(this)
                    .setTitle(R.string.network_error_dialog_title)
                    .setMessage(R.string.chat_error_dialog_text)
                    .setPositiveButton(R.string.dialog_ok_button) { dialog, _ ->
                        finish()
                        dialog.dismiss()
                    }
                    .create().show()
            }

            is ChatViewState.EstablishingChatSessionSuccess -> {
                snackbar?.dismiss()
            }

            is ChatViewState.WaitingForStranger -> {
                snackbar = Snackbar.make(root_chat_layout, R.string.chat_waiting_for_stranger, Snackbar.LENGTH_INDEFINITE)
                snackbar?.show()
            }

            is ChatViewState.StrangerHasConnected -> {
                snackbar?.dismiss()
            }
        }
    }
}