package com.alexvoz.cvirus.presentation.country_data

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexvoz.cvirus.data.country_data.CountryData
import com.alexvoz.cvirus.data.covid_data.network.Country
import com.alexvoz.cvirus.repository.country_data.CountryDataRepositoryImpl
import com.alexvoz.cvirus.repository.covid_data.CovidDataRepositoryImpl
import com.alexvoz.template.util.SingleLiveEvent
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@InternalCoroutinesApi
class CountryDataViewModel
@ViewModelInject
constructor(
    private val countryDataRepository: CountryDataRepositoryImpl,
    private val covidDataRepositoryImpl: CovidDataRepositoryImpl,
) : ViewModel() {

    private val _countryHistoryData = MutableLiveData<List<CountryData>>()
    val countryHistoryData: LiveData<List<CountryData>>
        get() = _countryHistoryData

    private val _countryData = SingleLiveEvent<Country>()
    val countryData: LiveData<Country>
        get() = _countryData

    fun initCountry(countryName: String) {
        viewModelScope.launch {
            covidDataRepositoryImpl.getCountryByName(countryName).firstOrNull()?.let {
                _countryData.value = it
            }
        }
    }

    fun getCountryData(country: Country) {
        viewModelScope.launch {
            countryDataRepository.getCountryData(country).onEach { countryData ->
                _countryHistoryData.value = countryData.getData
            }.launchIn(viewModelScope)
        }
    }
}