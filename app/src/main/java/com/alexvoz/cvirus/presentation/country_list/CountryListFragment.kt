package com.alexvoz.cvirus.presentation.country_list

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.alexvoz.cvirus.R
import com.alexvoz.cvirus.presentation.country_data.COUNTRY_NAME
import com.alexvoz.cvirus.presentation.main.SharedViewModel
import kotlinx.android.synthetic.main.fragment_countries_list.*
import kotlinx.coroutines.*


@InternalCoroutinesApi
class CountryListFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val viewModel: CountryListViewModel by activityViewModels()
    private lateinit var yourAdapterName: CountryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_countries_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUsersList()

        swipe_container.setOnRefreshListener {
            sharedViewModel.getCovidData()
        }

        sharedViewModel.covidData.observe(viewLifecycleOwner, Observer {
            swipe_container.isRefreshing = false
            if (it == null) return@Observer
            yourAdapterName.submitList(it.countries)
        })

        sharedViewModel.internetConnectionListener.observe(viewLifecycleOwner) {
            if (viewModel.reloadCountries && it) {
                sharedViewModel.getCovidData()
            }
            viewModel.reloadCountries = !it
        }

        searchCountry.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //DO nothing
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                sharedViewModel.covidData.value?.countries?.let {
                    yourAdapterName.submitList(it.filter { country ->
                        country.country.contains(
                            p0.toString(), true
                        )
                    })
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                //DO nothing
            }
        })

        app_toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menuSortByNewCases -> {
                    sharedViewModel.covidData.value?.countries?.let {
                        yourAdapterName.submitList(it.sortedBy { country -> country.newConfirmed })
                    }
                    true
                }

                R.id.menuSortByNewCasesDes -> {
                    sharedViewModel.covidData.value?.countries?.let {
                        yourAdapterName.submitList(it.sortedByDescending { country -> country.newConfirmed})
                    }
                    true
                }

                R.id.menuSortByNewDeaths -> {
                    sharedViewModel.covidData.value?.countries?.let {
                        yourAdapterName.submitList(it.sortedBy { country -> country.newDeaths })
                    }
                    true
                }

                R.id.menuSortByNewDeathsDes -> {
                    sharedViewModel.covidData.value?.countries?.let {
                        yourAdapterName.submitList(it.sortedByDescending { country -> country.newDeaths })
                    }
                    true
                }

                R.id.menuSortByNewRecovered -> {
                    sharedViewModel.covidData.value?.countries?.let {
                        yourAdapterName.submitList(it.sortedBy { country -> country.newRecovered })
                    }
                    true
                }

                R.id.menuSortByNewRecoveredDes -> {
                    sharedViewModel.covidData.value?.countries?.let {
                        yourAdapterName.submitList(it.sortedByDescending { country -> country.newRecovered })
                    }
                    true
                }

                else -> false
            }
        }
    }

    private fun initUsersList() {
        users_list.apply {
            layoutManager = LinearLayoutManager(context)

            yourAdapterName = CountryAdapter(
                onClick = {
                    swipe_container.isRefreshing = false
                    sharedViewModel.cancelAnyWorkingJob()

                    val bundle = bundleOf(COUNTRY_NAME to it.country)
                    findNavController().navigate(
                        R.id.action_countriesList_to_countryData,
                        bundle
                    )

                }
            )
            adapter = yourAdapterName
        }
    }
}