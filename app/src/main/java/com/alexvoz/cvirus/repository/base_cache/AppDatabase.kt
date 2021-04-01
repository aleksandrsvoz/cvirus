package com.alexvoz.cvirus.repository.base_cache

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.alexvoz.cvirus.data.country_data.cache.CountryDataCached
import com.alexvoz.cvirus.data.covid_data.cache.CovidDataCached
import com.alexvoz.cvirus.repository.country_data.cache.CountryDataDao
import com.alexvoz.cvirus.repository.covid_data.cache.CovidDataDao

@Database(
    entities = [
        CovidDataCached::class,
        CountryDataCached::class
    ], version = 1
)

@TypeConverters(
    Converters::class,
)

abstract class AppDatabase : RoomDatabase() {

    abstract fun covidDataDao(): CovidDataDao
    abstract fun countryDataDao(): CountryDataDao

    companion object {
        const val DATABASE_NAME: String = "app_db"
    }
}