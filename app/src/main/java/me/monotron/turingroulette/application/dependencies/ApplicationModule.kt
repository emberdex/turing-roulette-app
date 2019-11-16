package me.monotron.turingroulette.application.dependencies

import dagger.Module
import dagger.Provides
import me.monotron.turingroulette.api.TuringAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule {

    private val baseUrl: String = "http://api.turing-roulette.tech/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
    }

    @Provides
    @Singleton
    fun provideTuringApi(retrofit: Retrofit): TuringAPI {

        return retrofit.create(TuringAPI::class.java)
    }
}