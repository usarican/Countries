package com.utkusarican.countriesapplication.domain.mapper

import com.utkusarican.countriesapplication.data.local.entity.SavedCountry
import com.utkusarican.countriesapplication.data.remote.response.Country
import javax.inject.Inject

class CountryMapper @Inject constructor(){

    fun responseToEntity(
        saved : Boolean,
        response : Country
    ) : SavedCountry =
        with(response){
            SavedCountry(
                countryCode = code,
                countryName = name,
                saved = saved
            )
        }
}