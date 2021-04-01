package com.alexvoz.cvirus.repository.covid_data.network

import com.alexvoz.cvirus.data.covid_data.network.CovidDataResponse
import retrofit2.http.GET

interface CovidDataRetrofit {
    @GET("summary")
    suspend fun getSummary(): CovidDataResponse

}