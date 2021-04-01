package com.alexvoz.cvirus.presentation.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.alexvoz.cvirus.R
import com.alexvoz.cvirus.util.SharedViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.InternalCoroutinesApi

@AndroidEntryPoint
@InternalCoroutinesApi
class MainActivity : AppCompatActivity() {
    lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModels()
    private val sharedViewModel: SharedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController


        sharedViewModel.internetConnectionListener.observe(this) {
            no_wifi.visibility = if (it) View.INVISIBLE else View.VISIBLE
        }

        bnvMainTabs.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->

            if (item.title.toString() != navController.currentDestination?.label) {
                navController.navigate(
                    when (item.itemId) {
                        R.id.menuGlobal -> R.id.action_countriesListFragment_to_globalDataFragment

                        R.id.menuCountries -> R.id.action_globalDataFragment_to_countriesListFragment

                        else -> R.id.action_globalDataFragment_to_countriesListFragment
                    }
                )
            }
            return@OnNavigationItemSelectedListener true
        })

    }
}