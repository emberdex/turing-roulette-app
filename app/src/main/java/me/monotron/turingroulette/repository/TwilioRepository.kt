package me.monotron.turingroulette.repository

import android.content.Context
import android.util.Log
import com.twilio.chat.*
import kotlinx.coroutines.runBlocking
import me.monotron.turingroulette.api.TuringAPI
import me.monotron.turingroulette.api.responses.Identity
import me.monotron.turingroulette.api.responses.TwilioSids
import javax.inject.Inject

// nothing is sacred about this God forsaken code
class TwilioRepository @Inject constructor(
    val api: TuringAPI
) {

    var token: Identity? = null
    var twilioSids: TwilioSids? = null
    private var applicationContext: Context? = null

    lateinit var client: ChatClient
    lateinit var channel: Channel

    val clientCallbackListener: CallbackListener<ChatClient> = (object : CallbackListener<ChatClient>() {
        override fun onSuccess(p0: ChatClient?) {
            client = p0!!
            initialiseChannel()
        }
    })

    val channelCallbackListener: CallbackListener<Channel> = (object : CallbackListener<Channel>() {
        override fun onSuccess(p0: Channel?) {
            channel = p0!!
            initialiseMessageListener()
        }
    })

    val messageListener: ChannelListener = (object : ChannelListener {
        override fun onMemberDeleted(p0: Member?) {}
        override fun onTypingEnded(p0: Channel?, p1: Member?) {}
        override fun onMessageDeleted(p0: Message?) {}
        override fun onMemberAdded(p0: Member?) {}
        override fun onTypingStarted(p0: Channel?, p1: Member?) {}
        override fun onSynchronizationChanged(p0: Channel?) {}
        override fun onMessageUpdated(p0: Message?, p1: Message.UpdateReason?) {}
        override fun onMemberUpdated(p0: Member?, p1: Member.UpdateReason?) {}

        override fun onMessageAdded(p0: Message?) {
            Log.i("TOBY", p0?.messageBody ?: "Message was null")
        }
    })

    val statusListener: StatusListener = (object : StatusListener() {
        override fun onSuccess() {}
    })

    fun setApplicationContext(application: Context) {
        this.applicationContext = application
    }

    fun initialiseChatRoom() {
        refreshToken()
        getTwilioSids()

        Log.i("TOBY", token?.token ?: "Token was null")
        Log.i("TOBY", token?.identity ?: "Identity was null")
        Log.i("TOBY", twilioSids?.channelSid ?: "channelSid was null")
        Log.i("TOBY", twilioSids?.serviceSid ?: "serviceSid was null")

        ChatClient.create(applicationContext!!, token?.token!!, ChatClient.Properties.Builder().createProperties(), clientCallbackListener)
    }

    fun initialiseChannel() {

        client.channels.getChannel(twilioSids?.channelSid, channelCallbackListener)
    }

    fun initialiseMessageListener() {

        channel.join(statusListener)
        channel.addListener(messageListener)
    }

    private fun refreshToken() {

        runBlocking {
            val returned = api.getToken()

            if(returned.isSuccessful) {
                token = returned.body()
            } else {
                throw RuntimeException("Failed to fetch token")
            }
        }
    }

    private fun getTwilioSids() {

        runBlocking {
            val returned = api.getTwilioSids(token?.identity!!)

            if(returned.isSuccessful) {
                twilioSids = returned.body()
            } else {
                throw RuntimeException("Failed to fetch Twilio SIDs.")
            }
        }
    }
}