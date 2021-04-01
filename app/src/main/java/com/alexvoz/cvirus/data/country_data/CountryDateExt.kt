package com.alexvoz.cvirus.data.country_data

import com.alexvoz.cvirus.data.country_data.cache.CountryDataCached
import com.alexvoz.cvirus.data.country_data.network.CountryDataResponse

fun CountryData.asCached() = CountryDataCached(
    id = date + country,
    active = active,
    confirmed = confirmed,
    country = country,
    countryCode = countryCode,
    date = date,
    deaths = deaths,
    lat = lat,
    locationID = locationID,
    lon = lon,
    recovered = recovered
)

fun CountryDataResponse.asCountryData() = CountryData(
    active = active,
    confirmed = confirmed,
    country = country,
    countryCode = countryCode,
    date = date,
    deaths = deaths,
    lat = lat,
    locationID = locationID ?: "",
    lon = lon,
    recovered = recovered
)

fun CountryDataCached.asCountryData() = CountryData(
    active = active,
    confirmed = confirmed,
    country = country,
    countryCode = countryCode,
    date = date,
    deaths = deaths,
    lat = lat,
    locationID = locationID,
    lon = lon,
    recovered = recovered
)