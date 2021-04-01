package com.alexvoz.cvirus.repository.country_data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alexvoz.cvirus.data.country_data.cache.CountryDataCached
import kotlinx.coroutines.flow.Flow

@Dao
interface CountryDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(countryDataCached: CountryDataCached)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(countriesDataCached: List<CountryDataCached>)

    @Query("SELECT * FROM country_data WHERE country = :countryName")
    fun get(countryName: String): Flow<List<CountryDataCached>>
}