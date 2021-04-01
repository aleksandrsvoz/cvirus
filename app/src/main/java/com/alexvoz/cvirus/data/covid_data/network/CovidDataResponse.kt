package com.alexvoz.cvirus.data.covid_data.network


import com.google.gson.annotations.SerializedName

data class CovidDataResponse(
    @SerializedName("Countries")
    val countries: List<Country>,
    @SerializedName("Date")
    val date: String,
    @SerializedName("Global")
    val global: Global,
    @SerializedName("Message")
    val message: String
)