package com.alexvoz.cvirus.data.country_data.network


import com.google.gson.annotations.SerializedName

data class CountryDataResponse(
    @SerializedName("Active")
    val active: Int,
    @SerializedName("Confirmed")
    val confirmed: Int,
    @SerializedName("Country")
    val country: String,
    @SerializedName("CountryCode")
    val countryCode: String,
    @SerializedName("Date")
    val date: String,
    @SerializedName("Deaths")
    val deaths: Int,
    @SerializedName("Lat")
    val lat: String,
    @SerializedName("LocationID")
    val locationID: String?,
    @SerializedName("Lon")
    val lon: String,
    @SerializedName("Recovered")
    val recovered: Int
)