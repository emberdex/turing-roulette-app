package me.monotron.turingroulette.api

import retrofit2.Call
import retrofit2.http.GET

interface TuringAPI {

    @GET("health")
    fun healthCheck(): Call<Nothing>
}