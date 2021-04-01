package com.alexvoz.cvirus.data.country_data


data class CountryData(
    val active: Int,
    val confirmed: Int,
    val country: String,
    val countryCode: String,
    val date: String,
    val deaths: Int,
    val lat: String,
    val locationID: String,
    val lon: String,
    val recovered: Int
)