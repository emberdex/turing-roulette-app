package me.monotron.turingroulette.repository

import android.annotation.SuppressLint
import me.monotron.turingroulette.api.TuringAPI
import javax.inject.Inject

class TuringRepository @Inject constructor(
    val api: TuringAPI
) {

    var isHealthy: Boolean? = null

    @SuppressLint("CheckResult")
    suspend fun performHealthCheck() = api.healthCheck()
}