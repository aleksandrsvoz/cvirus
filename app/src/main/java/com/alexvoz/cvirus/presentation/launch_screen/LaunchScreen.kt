package com.alexvoz.cvirus.presentation.launch_screen

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alexvoz.cvirus.presentation.main.MainActivity
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class LaunchScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MainActivity.startActivity(this)
    }
}