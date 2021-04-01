package com.alexvoz.cvirus.presentation.country_data

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.alexvoz.cvirus.R
import com.alexvoz.cvirus.data.covid_data.network.Country
import kotlinx.android.synthetic.main.fragment_country_data.*
import kotlinx.coroutines.*

const val COUNTRY_NAME = "country_name"

@InternalCoroutinesApi
class CountryDataFragment : Fragment() {

    private val countryDataViewModel: CountryDataViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_country_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val countryName = arguments?.getString(COUNTRY_NAME)

        countryDataViewModel.countryData.observe(viewLifecycleOwner, {
            lcCountryChart.setData(it)
        })
        CoroutineScope(Job() + Dispatchers.IO).launch {
            countryName?.let {

                val country = countryDataViewModel.getCountry(it)

                countryDataViewModel.getCountryData(country)

                withContext(Dispatchers.Main) {
                    setData(country)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setData(country: Country) {
        tvCountryNewCases.text = country.newConfirmed.toString()
        tvCountryCasesTotal.text = "(Total: ${country.totalConfirmed})"

        tvCountryNewDeaths.text = country.newDeaths.toString()
        tvCountryDeathsTotal.text = "(Total: ${country.totalDeaths})"

        tvCountryNewRecovered.text = country.newRecovered.toString()
        tvCountryRecoveredTotal.text = "(Total: ${country.totalRecovered})"
    }
}