package com.utkusarican.countriesapplication.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "SavedCountry")
data class SavedCountry(
    @PrimaryKey
    val countryCode : String,
    val countryName : String,
    val saved : Boolean
)
