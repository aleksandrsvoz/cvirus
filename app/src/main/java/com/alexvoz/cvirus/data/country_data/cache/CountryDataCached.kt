package com.alexvoz.cvirus.data.country_data.cache


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country_data")
data class CountryDataCached(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "active")
    val active: Int,

    @ColumnInfo(name = "confirmed")
    val confirmed: Int,

    @ColumnInfo(name = "country")
    val country: String,

    @ColumnInfo(name = "countryCode")
    val countryCode: String,

    @ColumnInfo(name = "date")
    val date: String,

    @ColumnInfo(name = "deaths")
    val deaths: Int,

    @ColumnInfo(name = "lat")
    val lat: String,

    @ColumnInfo(name = "locationID")
    val locationID: String,

    @ColumnInfo(name = "lon")
    val lon: String,

    @ColumnInfo(name = "recovered")
    val recovered: Int
)
