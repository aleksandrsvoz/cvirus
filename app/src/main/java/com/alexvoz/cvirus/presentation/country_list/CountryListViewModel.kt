package com.alexvoz.cvirus.presentation.country_list

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class CountryListViewModel
@ViewModelInject
constructor(
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var reloadCountries: Boolean = false
}