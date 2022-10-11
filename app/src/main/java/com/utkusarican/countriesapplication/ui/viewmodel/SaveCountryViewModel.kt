package com.utkusarican.countriesapplication.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.utkusarican.countriesapplication.data.local.entity.SavedCountry
import com.utkusarican.countriesapplication.data.remote.response.Country
import com.utkusarican.countriesapplication.domain.repository.CountriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SaveCountryViewModel @Inject constructor(
    private val countriesRepository: CountriesRepository
) : ViewModel() {

    val getAllSavedCountries = countriesRepository.getAllSavedCountries()

    fun saveCountry(country: Country) =
        viewModelScope.launch(Dispatchers.IO) {
            countriesRepository.saveCountry(country)
        }

    fun deleteCountry(savedCountry: SavedCountry) =
        viewModelScope.launch(Dispatchers.IO) {
            countriesRepository.deleteCountry(savedCountry)
        }

    fun getSavedInformation(countryCode : String) =
        countriesRepository.getSavedInformation(countryCode)

    fun editSavedInformation(countryCode: String,save : Boolean) =
        countriesRepository.editSavedInformation(countryCode,save)

}