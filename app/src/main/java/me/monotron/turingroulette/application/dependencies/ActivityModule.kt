package me.monotron.turingroulette.application.dependencies

import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.ContributesAndroidInjector
import me.monotron.turingroulette.base.BaseActivity
import me.monotron.turingroulette.chat.ChatActivity
import me.monotron.turingroulette.startup.StartupActivity

@Module
abstract class ActivityModule : AndroidInjector<BaseActivity> {

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun bindStartupActivity(): StartupActivity

    @ActivityScope
    @ContributesAndroidInjector
    abstract fun bindChatActivity(): ChatActivity
}