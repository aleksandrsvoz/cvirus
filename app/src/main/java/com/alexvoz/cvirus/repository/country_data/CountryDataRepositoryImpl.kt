package com.alexvoz.cvirus.repository.country_data

import com.alexvoz.cvirus.data.country_data.CountryData
import com.alexvoz.cvirus.data.country_data.asCached
import com.alexvoz.cvirus.data.country_data.asCountryData
import com.alexvoz.cvirus.data.covid_data.network.Country
import com.alexvoz.cvirus.repository.country_data.cache.CountryDataDao
import com.alexvoz.cvirus.repository.country_data.network.CountryDataRetrofit
import com.alexvoz.cvirus.util.Resource
import kotlinx.coroutines.flow.*


class CountryDataRepositoryImpl(
    private val countryDataDao: CountryDataDao,
    private val countryDataRetrofit: CountryDataRetrofit
) {

    suspend fun getCountryData(country: Country): Flow<Resource<List<CountryData>?>> =
        networkBoundResource(
            fromCache = {
                countryDataDao.get(country.country).map { list -> list.map { it.asCountryData() } }
            },
            requestCall = {
                countryDataRetrofit.getCountryData(country.country)
                    .map { it.asCountryData() }
            },
            saveInCache = {
                countryDataDao.insertAll(it.map { country -> country.asCached() })
            }
        )

    private inline fun <ResponseType, ResultType> networkBoundResource(
        crossinline fromCache: () -> Flow<ResultType>,
        crossinline requestCall: suspend () -> ResponseType,
        crossinline saveInCache: suspend (ResponseType) -> Unit
    ) = flow {

        val flow = run {
            try {
                saveInCache(requestCall())
                fromCache().map { Resource.Success(it) }
            } catch (throwable: Throwable) {
                fromCache().map { Resource.Error(it, throwable) }
            }
        }
        emitAll(flow)
    }
}