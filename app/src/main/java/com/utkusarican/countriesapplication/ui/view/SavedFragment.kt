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
import com.utkusarican.countriesapplication.databinding.FragmentSavedBinding
import com.utkusarican.countriesapplication.ui.adapter.SavedCountriesAdapter
import com.utkusarican.countriesapplication.ui.viewmodel.SaveCountryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SavedFragment : Fragment() {

    private var _binding : FragmentSavedBinding? = null
    private val binding get() = _binding!!
    private val saveCountryViewModel : SaveCountryViewModel by viewModels()
    private lateinit var savedCountriesAdapter : SavedCountriesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        savedCountriesAdapter = SavedCountriesAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSavedBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setup()
        setupViewModel()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setup(){
        binding.apply {
            savedCountriesList.layoutManager = LinearLayoutManager(context)
            savedCountriesList.adapter = savedCountriesAdapter
            savedCountriesList.setHasFixedSize(true)
            savedCountriesAdapter.setOnItemClickListener(object : SavedCountriesAdapter.onItemClickListener{
                override fun onItemClicked(savedCountry: SavedCountry) {
                    saveCountryViewModel.deleteCountry(savedCountry)
                    saveCountryViewModel.editSavedInformation(savedCountry.countryCode,false)
                    Toast.makeText(this@SavedFragment.context,"You Deleted This Country: ${savedCountry.countryName}", Toast.LENGTH_SHORT).show()
                }

            })
        }
    }

    private fun setupViewModel(){
        saveCountryViewModel.getAllSavedCountries.observe(viewLifecycleOwner){
            savedCountriesAdapter.submitList(it)
        }
    }

}