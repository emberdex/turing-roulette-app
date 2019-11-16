package me.monotron.turingroulette.repository

import me.monotron.turingroulette.api.TuringAPI
import javax.inject.Inject

class TuringRepository @Inject constructor(
    val api: TuringAPI
) {

    var isHealthy: Boolean? = null

    fun performHealthCheck(): Boolean {

        if (isHealthy != null) {
            return isHealthy == true
        }

        isHealthy = try {
            api.healthCheck()
            true
        } catch (e: Exception) {
            false
        }

        return isHealthy == true
    }
}