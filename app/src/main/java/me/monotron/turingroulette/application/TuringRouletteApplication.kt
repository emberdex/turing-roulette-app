package me.monotron.turingroulette.application

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import me.monotron.gistbot.application.dependencies.ApplicationComponent
import me.monotron.gistbot.application.dependencies.DaggerApplicationComponent
import javax.inject.Inject

class TuringRouletteApplication : Application(), HasActivityInjector {

    @Inject lateinit var activityDispatchingInjector: DispatchingAndroidInjector<Activity>

    lateinit var dependencyGraph: ApplicationComponent

    override fun onCreate() {

        super.onCreate()

        dependencyGraph = DaggerApplicationComponent.builder()
            .application(this)
            .build()

        dependencyGraph.inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityDispatchingInjector
    }
}
