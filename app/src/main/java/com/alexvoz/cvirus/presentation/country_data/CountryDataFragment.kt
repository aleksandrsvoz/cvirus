package com.alexvoz.cvirus.presentation.country_data

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.alexvoz.cvirus.R
import com.alexvoz.cvirus.data.covid_data.network.Country
import com.alexvoz.cvirus.util.getNumberWithSpaces
import kotlinx.android.synthetic.main.block_today_data.view.*
import kotlinx.android.synthetic.main.fragment_country_data.*
import kotlinx.coroutines.InternalCoroutinesApi

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

        initObservers()
        initData()
    }

    private fun initData() {
        arguments?.getString(COUNTRY_NAME)?.let {
            countryDataViewModel.initCountry(it)
        }
    }

    private fun initObservers() {
        countryDataViewModel.countryData.observe(viewLifecycleOwner, {
            setData(it)

            countryDataViewModel.getCountryData(it)
        })

        countryDataViewModel.countryHistoryData.observe(viewLifecycleOwner, {
            lcCountryChart.setData(it)
        })
    }

    private fun setData(country: Country) {
        iTodayDataBlock.tvNewCases.text = country.newConfirmed.getNumberWithSpaces()
        iTodayDataBlock.tvRecovered.text = country.newRecovered.getNumberWithSpaces()
        iTodayDataBlock.tvDeaths.text = country.newDeaths.getNumberWithSpaces()
    }
}