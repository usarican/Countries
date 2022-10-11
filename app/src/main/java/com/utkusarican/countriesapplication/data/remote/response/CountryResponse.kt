package com.utkusarican.countriesapplication.data.remote.response

import com.utkusarican.countriesapplication.data.local.sp.SavedSharedPreferences

data class CountryResponse(
    val data : List<Country>
)


data class Country(
    val code : String,
    val name : String
){
}
