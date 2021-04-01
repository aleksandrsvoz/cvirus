package com.alexvoz.cvirus.util

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexvoz.cvirus.data.covid_data.CovidData
import com.alexvoz.cvirus.repository.base_network.InternetConnectionListener
import com.alexvoz.cvirus.repository.country_data.CountryDataRepositoryImpl
import com.alexvoz.cvirus.repository.covid_data.CovidDataRepositoryImpl
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class SharedViewModel @ViewModelInject constructor(
    val internetConnectionListener: InternetConnectionListener,
    private val covidDataRepositoryImpl: CovidDataRepositoryImpl,
    private val countryDataRepositoryImpl: CountryDataRepositoryImpl,
) :
    ViewModel() {

    private var job: Job? = null
    private val _covidData = MutableLiveData<CovidData>()
    val covidData: LiveData<CovidData>
        get() = _covidData

    init {
        getCovidData()
    }

    fun getCovidData() {
        cancelAnyWorkingJob()
        job = viewModelScope.launch {
            covidDataRepositoryImpl.getCovidData().onEach { covidData ->
                _covidData.value = covidData.getData
            }.launchIn(viewModelScope)
        }
    }

    fun cancelAnyWorkingJob() {
        job?.cancel()
    }

}
