package com.alexvoz.cvirus.di

import com.alexvoz.cvirus.repository.country_data.CountryDataRepositoryImpl
import com.alexvoz.cvirus.repository.country_data.cache.CountryDataDao
import com.alexvoz.cvirus.repository.country_data.network.CountryDataRetrofit
import com.alexvoz.cvirus.repository.covid_data.CovidDataRepositoryImpl
import com.alexvoz.cvirus.repository.covid_data.cache.CovidDataDao
import com.alexvoz.cvirus.repository.covid_data.network.CovidDataRetrofit
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton


@InstallIn(ApplicationComponent::class)
@Module
object CovidDataRepositoryModule {
    @Singleton
    @Provides
    fun provideRepository(
        covidDataDao: CovidDataDao,
        covidDataRetrofit: CovidDataRetrofit
    ): CovidDataRepositoryImpl = CovidDataRepositoryImpl(covidDataDao, covidDataRetrofit)
}

@InstallIn(ApplicationComponent::class)
@Module
object CountryDataRepositoryModule {
    @Singleton
    @Provides
    fun provideRepository(
        countryDataDao: CountryDataDao,
        countryDataRetrofit: CountryDataRetrofit
    ): CountryDataRepositoryImpl = CountryDataRepositoryImpl(countryDataDao, countryDataRetrofit)
}