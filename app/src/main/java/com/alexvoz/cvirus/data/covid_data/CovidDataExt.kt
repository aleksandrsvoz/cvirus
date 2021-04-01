package com.alexvoz.cvirus.data.covid_data

import com.alexvoz.cvirus.data.covid_data.cache.CovidDataCached
import com.alexvoz.cvirus.data.covid_data.network.CovidDataResponse

fun CovidDataCached?.asCovidData() = if (this == null) null else CovidData(
    countries = countries,
    date = date,
    global = global,
    message = message
)

fun CovidDataResponse.asCovidData() = CovidData(
    countries = countries,
    date = date,
    global = global,
    message = message
)

fun CovidData.asCovidDataCached() = CovidDataCached(
    countries = countries,
    date = date,
    global = global,
    message = message
)