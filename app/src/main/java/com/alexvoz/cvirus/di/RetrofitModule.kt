package com.alexvoz.cvirus.di

import android.content.Context
import com.alexvoz.cvirus.repository.base_network.interceptor.NetworkConnectionInterceptor
import com.alexvoz.cvirus.repository.country_data.network.CountryDataRetrofit
import com.alexvoz.cvirus.repository.covid_data.network.CovidDataRetrofit
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val MAX_REQUEST_AT_A_TIME = 1

@InstallIn(ApplicationComponent::class)
@Module
object RetrofitModule {
    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder().create()
    }

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson, @ApplicationContext appContext: Context): Retrofit.Builder {
        val dispatcher = Dispatcher()
        dispatcher.maxRequests = MAX_REQUEST_AT_A_TIME

        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(5, TimeUnit.MINUTES)
            .connectTimeout(90, TimeUnit.SECONDS)
            .addInterceptor(NetworkConnectionInterceptor(appContext))
            .dispatcher(dispatcher)
            .build()

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://api.covid19api.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
    }

    @Singleton
    @Provides
    fun provideCovidDataService(retrofit: Retrofit.Builder): CovidDataRetrofit {
        return retrofit.build().create(CovidDataRetrofit::class.java)
    }

    @Singleton
    @Provides
    fun provideCountryDataService(retrofit: Retrofit.Builder): CountryDataRetrofit {
        return retrofit.build().create(CountryDataRetrofit::class.java)
    }

}