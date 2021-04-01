package com.alexvoz.cvirus.presentation.covid_data

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.alexvoz.cvirus.R
import com.alexvoz.cvirus.data.covid_data.network.Global
import com.alexvoz.cvirus.util.SharedViewModel
import kotlinx.android.synthetic.main.fragment_global_data.*
import kotlinx.coroutines.InternalCoroutinesApi


@InternalCoroutinesApi
class CovidGlobalDataFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_global_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedViewModel.covidData.observe(viewLifecycleOwner, {
            it?.let { covidData -> setData(covidData.global) }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun setData(global: Global) {
        newCases.text = global.newConfirmed.toString()
        casesTotal.text = "(Total: ${global.totalConfirmed})"

        newDeaths.text = global.newDeaths.toString()
        deathsTotal.text = "(Total: ${global.totalDeaths})"

        newRecovered.text = global.newRecovered.toString()
        recoveredTotal.text = "(Total: ${global.totalRecovered})"
    }
}