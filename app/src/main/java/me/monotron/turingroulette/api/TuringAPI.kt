package me.monotron.turingroulette.api

import retrofit2.Response
import retrofit2.http.GET

interface TuringAPI {

    @GET("health")
    suspend fun healthCheck(): Response<Void>
}