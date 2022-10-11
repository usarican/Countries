package com.utkusarican.countriesapplication.di

import android.content.SharedPreferences
import com.utkusarican.countriesapplication.core.data.CountriesDatabase
import com.utkusarican.countriesapplication.data.local.CountriesDAO
import com.utkusarican.countriesapplication.data.local.sp.SavedSharedPreferences
import com.utkusarican.countriesapplication.data.remote.CountriesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit


@Module
@InstallIn(SingletonComponent::class)
object CountriesAppModule {

    @Provides
    fun provideMovieService(retrofit: Retrofit): CountriesService =
        retrofit.create(CountriesService::class.java)

    @Provides
    fun provideCountriesDao(countriesDatabase: CountriesDatabase) : CountriesDAO =
        countriesDatabase.countriesDao()

    @Provides
    fun provideSavedSharedPreferences(sharedPreferences: SharedPreferences) =
        SavedSharedPreferences(sharedPreferences)

}