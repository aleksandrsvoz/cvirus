package com.alexvoz.cvirus.data.covid_data.cache


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alexvoz.cvirus.data.covid_data.network.Country
import com.alexvoz.cvirus.data.covid_data.network.Global

@Entity(tableName = "covid_data")
data class CovidDataCached(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "date")
    val date: String,

    @ColumnInfo(name = "countries")
    val countries: List<Country>,

    @ColumnInfo(name = "global")
    val global: Global,

    @ColumnInfo(name = "message")
    val message: String
)