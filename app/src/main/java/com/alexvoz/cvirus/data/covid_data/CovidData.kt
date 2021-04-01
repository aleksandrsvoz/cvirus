package com.alexvoz.cvirus.data.covid_data


import com.alexvoz.cvirus.data.covid_data.network.Country
import com.alexvoz.cvirus.data.covid_data.network.Global

data class CovidData(
    val countries: List<Country>,
    val date: String,
    val global: Global,
    val message: String
)