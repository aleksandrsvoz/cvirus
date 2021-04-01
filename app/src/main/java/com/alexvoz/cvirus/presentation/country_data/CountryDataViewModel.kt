package com.alexvoz.cvirus.presentation.country_data

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.alexvoz.cvirus.data.country_data.CountryData
import com.alexvoz.cvirus.data.covid_data.network.Country
import com.alexvoz.cvirus.repository.country_data.CountryDataRepositoryImpl
import com.alexvoz.cvirus.repository.covid_data.CovidDataRepositoryImpl
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@InternalCoroutinesApi
class CountryDataViewModel
@ViewModelInject
constructor(
    private val countryDataRepository: CountryDataRepositoryImpl,
    private val covidDataRepositoryImpl: CovidDataRepositoryImpl,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _countryData = MutableLiveData<List<CountryData>>()
    val countryData: LiveData<List<CountryData>>
        get() = _countryData

   suspend fun getCountry(countryName: String) = covidDataRepositoryImpl.getCountryByName(countryName).first()

    fun getCountryData(country: Country) {
        viewModelScope.launch {
            countryDataRepository.getCountryData(country).onEach { countryData ->
                _countryData.value = countryData.getData
            }.launchIn(viewModelScope)
        }
    }
}