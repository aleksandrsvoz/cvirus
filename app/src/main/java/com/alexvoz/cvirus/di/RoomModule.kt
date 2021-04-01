package com.alexvoz.cvirus.di

import android.content.Context
import androidx.room.Room
import com.alexvoz.cvirus.repository.base_cache.AppDatabase
import com.alexvoz.cvirus.repository.country_data.cache.CountryDataDao
import com.alexvoz.cvirus.repository.covid_data.cache.CovidDataDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object RoomModule {

    @Singleton
    @Provides
    fun provideDb(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideCovidDataDao(appDatabase: AppDatabase): CovidDataDao {
        return appDatabase.covidDataDao()
    }

    @Singleton
    @Provides
    fun provideCountryDataDao(appDatabase: AppDatabase): CountryDataDao {
        return appDatabase.countryDataDao()
    }

}