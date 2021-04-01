package com.alexvoz.cvirus.repository.covid_data

import com.alexvoz.cvirus.data.covid_data.CovidData
import com.alexvoz.cvirus.data.covid_data.asCovidData
import com.alexvoz.cvirus.data.covid_data.asCovidDataCached
import com.alexvoz.cvirus.repository.covid_data.cache.CovidDataDao
import com.alexvoz.cvirus.repository.covid_data.network.CovidDataRetrofit
import com.alexvoz.cvirus.util.Resource
import kotlinx.coroutines.flow.*


class CovidDataRepositoryImpl(
    private val covidDataDao: CovidDataDao,
    private val covidDataRetrofit: CovidDataRetrofit
) {

    fun getCountryByName(countryName: String) =
        covidDataDao.getOne()
            .map { it.countries.first { countryItem -> countryItem.country == countryName } }

    suspend fun getCovidData(): Flow<Resource<CovidData?>> =
        networkBoundResource(
            fromCache = {
                covidDataDao.getOne().map { it.asCovidData() }
            },
            requestCall = {
                covidDataRetrofit.getSummary().asCovidData()
            },
            saveInCache = {
                covidDataDao.insert(it.asCovidDataCached())
            }
        )

    private inline fun <ResponseType, ResultType> networkBoundResource(
        crossinline fromCache: () -> Flow<ResultType>,
        crossinline requestCall: suspend () -> ResponseType,
        crossinline saveInCache: suspend (ResponseType) -> Unit
    ) = flow {

        emit(Resource.Loading(fromCache().first()))

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