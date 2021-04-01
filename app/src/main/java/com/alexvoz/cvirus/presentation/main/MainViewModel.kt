package com.alexvoz.cvirus.presentation.main

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
open class MainViewModel
@ViewModelInject
constructor(@Assisted private val savedStateHandle: SavedStateHandle) : ViewModel() {
    val a = 1


}