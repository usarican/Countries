package com.utkusarican.countriesapplication.ui.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.utkusarican.countriesapplication.data.local.entity.SavedCountry
import com.utkusarican.countriesapplication.data.remote.response.Country
import com.utkusarican.countriesapplication.data.remote.response.CountryDetail
import com.utkusarican.countriesapplication.databinding.FragmentCountryDetailBinding
import com.utkusarican.countriesapplication.ui.viewmodel.CountryDetailViewModel
import com.utkusarican.countriesapplication.ui.viewmodel.SaveCountryViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CountryDetailFragment : Fragment() {

    private val countryDetailViewModel : CountryDetailViewModel by viewModels()
    private val saveCountriesViewModel : SaveCountryViewModel by viewModels()
    private var _binding : FragmentCountryDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var countryCode : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCountryDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            countryCode = CountryDetailFragmentArgs.fromBundle(it).countryCode
        }
        getCountryDetails(countryCode)
        viewModelSetup()
        backButton(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun viewModelSetup(){
        countryDetailViewModel.getCountryInformation().observe(viewLifecycleOwner) { countryDetails ->
            countryDetails?.let {
                setUp(countryDetails)
                getLoadingInformatin()
                getWebViewImage(countryDetails.image)

            }

        }
    }

    private fun setUp(countryDetail : CountryDetail){
        binding.apply {

            countryDetailCountryName.text = countryDetail.name
            countryDetailCountryCode.text = countryDetail.code
            savedIcon.isChecked = saveCountriesViewModel.getSavedInformation(countryDetail.code)
            savedIcon.setOnClickListener {
                if(savedIcon.isChecked){
                    val country = Country(
                        name = countryDetail.name,
                        code = countryDetail.code
                    )
                    saveCountriesViewModel.saveCountry(country)
                    saveCountriesViewModel.editSavedInformation(country.code,true)
                    Toast.makeText(this@CountryDetailFragment.context,"You Saved This Country: ${country.name}",
                        Toast.LENGTH_SHORT).show()
                }else {
                    val savedCountry = SavedCountry(
                        countryName = countryDetail.name,
                        countryCode = countryDetail.code,
                        saved = true
                    )
                    saveCountriesViewModel.deleteCountry(savedCountry)
                    saveCountriesViewModel.editSavedInformation(savedCountry.countryCode,false)
                    Toast.makeText(this@CountryDetailFragment.context,"You Deleted This Country: ${savedCountry.countryName}", Toast.LENGTH_SHORT).show()
                }
            }
            countryDetailDetailButton.setOnClickListener {
                val action = CountryDetailFragmentDirections.actionCountryDetailFragmentToWebFragment(countryDetail.wikiDataId)
                Navigation.findNavController(it).navigate(action)
            }

        }
    }

    private fun getLoadingInformatin(){
        binding.apply {
            countryDetailViewModel.getLoadingInformation().observe(viewLifecycleOwner){
                if(!it){
                    countryDetailProgressBar.visibility = View.VISIBLE
                    countryDetailCountryName.visibility = View.INVISIBLE
                    countryDetailImage.visibility = View.INVISIBLE
                    countryDetailCd.visibility = View.INVISIBLE
                    countryDetailDetailButton.visibility = View.INVISIBLE
                    countryDetailCountryCode.visibility = View.INVISIBLE
                }else {
                    countryDetailProgressBar.visibility = View.INVISIBLE
                    countryDetailImage.visibility = View.VISIBLE
                    countryDetailCountryName.visibility = View.VISIBLE
                    countryDetailCd.visibility = View.VISIBLE
                    countryDetailDetailButton.visibility = View.VISIBLE
                    countryDetailCountryCode.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun getCountryDetails(code : String) =
        countryDetailViewModel.getCountryDetail(code)

    private fun backButton(view:View){
        binding.apply {
            countryDetailBackButton.setOnClickListener {
                Navigation.findNavController(view).popBackStack()
            }
        }
    }

    private fun getWebViewImage(url: String){
        binding.apply {
            countryDetailImage.webViewClient = WebViewClient()
            countryDetailImage.setInitialScale(1)
            countryDetailImage.settings.useWideViewPort = true
            countryDetailImage.settings.loadWithOverviewMode = true
            countryDetailImage.settings.javaScriptEnabled = true
            countryDetailImage.loadUrl(url)
        }
    }


}