package me.monotron.turingroulette.startup

open class StartupViewState {
    object Initial : StartupViewState()
    object ServiceHealthCheck : StartupViewState()
    object ServiceHealthCheckSucceeded : StartupViewState()

    class ServiceHealthCheckFailed(val exception: Throwable) : StartupViewState()
}