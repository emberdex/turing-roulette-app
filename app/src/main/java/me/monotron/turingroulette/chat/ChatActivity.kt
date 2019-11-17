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
            UserMessage(RECEIVED, "sup nerd"),
            UserMessage(RECEIVED, "it might not be the prettiest thing I've ever seen but it totally works :D"),
            UserMessage(SENT, "hooray!"),
            UserMessage(SENT, "Here's a really long message to see how it handles large amounts of text. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
        )

        messageRecycler = message_recycler_view as RecyclerView
        messageListAdapter = MessageListAdapter(this, messages)
        messageRecycler.layoutManager = LinearLayoutManager(this)
        messageRecycler.adapter = messageListAdapter
    }

    override fun onStart() {
        super.onStart()
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