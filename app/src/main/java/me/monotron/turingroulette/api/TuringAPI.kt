package me.monotron.turingroulette.api

import me.monotron.turingroulette.api.responses.Identity
import me.monotron.turingroulette.api.responses.TwilioSids
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TuringAPI {

    @GET("health")
    suspend fun healthCheck(): Response<Void>

    @GET("token")
    suspend fun getToken(): Response<Identity>

    @GET("chat/find")
    suspend fun getTwilioSids(@Query("id") identity: String): Response<TwilioSids>
}