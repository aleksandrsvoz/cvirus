package com.alexvoz.cvirus.repository.covid_data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alexvoz.cvirus.data.covid_data.cache.CovidDataCached
import kotlinx.coroutines.flow.Flow

@Dao
interface CovidDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(covidDataCached: CovidDataCached)

    @Query("SELECT * FROM covid_data")
    fun get(): Flow<List<CovidDataCached>>

    @Query("SELECT * FROM covid_data")
    fun getOne(): Flow<CovidDataCached>
}