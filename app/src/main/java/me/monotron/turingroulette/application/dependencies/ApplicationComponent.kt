package me.monotron.turingroulette.application.dependencies

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import me.monotron.turingroulette.application.TuringRouletteApplication
import me.monotron.turingroulette.base.BaseActivity
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ApplicationModule::class,
    ActivityModule::class,
    FragmentModule::class
])
interface ApplicationComponent : AndroidInjector<TuringRouletteApplication> {

    @Component.Builder
    interface Builder {

        @BindsInstance fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    override fun inject(application: TuringRouletteApplication)

    fun inject(activity: BaseActivity)
}