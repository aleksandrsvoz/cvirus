package com.alexvoz.cvirus.repository.country_data.network

import com.alexvoz.cvirus.data.country_data.network.CountryDataResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CountryDataRetrofit {
    @GET("/total/dayone/country/{countryName}")
    suspend fun getCountryData(
        @Path("countryName") countryName: String
    ): List<CountryDataResponse>
}