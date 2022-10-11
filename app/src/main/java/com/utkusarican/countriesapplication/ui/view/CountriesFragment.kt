package com.utkusarican.countriesapplication.ui.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.utkusarican.countriesapplication.R
import com.utkusarican.countriesapplication.data.local.entity.SavedCountry
import com.utkusarican.countriesapplication.data.local.sp.SavedSharedPreferences
import com.utkusarican.countriesapplication.data.remote.response.Country
import com.utkusarican.countriesapplication.databinding.FragmentCountriesBinding
import com.utkusarican.countriesapplication.ui.adapter.CountriesAdapter
import com.utkusarican.countriesapplication.ui.viewmodel.CountriesViewModel
import com.utkusarican.countriesapplication.ui.viewmodel.SaveCountryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountriesFragment : Fragment(R.layout.fragment_countries) {

    private val countriesViewModel : CountriesViewModel by viewModels()
    private val saveCountryViewModel : SaveCountryViewModel by viewModels()
    private var _binding : FragmentCountriesBinding? = null
    private val binding get() = _binding!!
    private lateinit var countriesAdapter : CountriesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        countriesAdapter = CountriesAdapter(context)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCountriesBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setup()
        setupViewModel()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setup(){
        binding.apply {
            countriesList.layoutManager = LinearLayoutManager(context)
            countriesList.adapter = countriesAdapter
            countriesList.setHasFixedSize(true)
            countriesAdapter.setOnItemClickListener(object : CountriesAdapter.onItemClickListener{
                override fun onItemClicked(country: Country, boolean: Boolean) {
                    if(boolean){
                        saveCountryViewModel.saveCountry(country)
                        countriesViewModel.editSavedInformation(country.code,boolean)
                        Toast.makeText(this@CountriesFragment.context,"You Saved This Country: ${country.name}",Toast.LENGTH_SHORT).show()
                    }else {
                        val savedCountry = SavedCountry(
                            countryName = country.name,
                            countryCode = country.code,
                            saved = true
                        )
                        saveCountryViewModel.deleteCountry(savedCountry)
                        countriesViewModel.editSavedInformation(country.code,boolean)
                        Toast.makeText(this@CountriesFragment.context,"You Deleted This Country: ${savedCountry.countryName}", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

    private fun setupViewModel(){
        countriesViewModel.countries.observe(viewLifecycleOwner){
            countriesAdapter.submitData(viewLifecycleOwner.lifecycle,it)
        }
    }

}