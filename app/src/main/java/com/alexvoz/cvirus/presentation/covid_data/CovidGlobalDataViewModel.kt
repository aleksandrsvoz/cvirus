package com.alexvoz.cvirus.presentation.covid_data

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.alexvoz.cvirus.repository.covid_data.CovidDataRepositoryImpl
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class CovidGlobalDataViewModel
@ViewModelInject
constructor(
    private val covidDataRepositoryImpl: CovidDataRepositoryImpl,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

}