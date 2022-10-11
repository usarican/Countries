package com.utkusarican.countriesapplication.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.Navigation
import com.utkusarican.countriesapplication.R
import com.utkusarican.countriesapplication.databinding.FragmentWebBinding

class WebFragment : Fragment() {

    private var _binding : FragmentWebBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWebBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val wikiDataId = WebFragmentArgs.fromBundle(it).countryWikiData
            getWebView(wikiDataId = wikiDataId)
        }
        backButton(view)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getWebView(wikiDataId: String){
        binding.apply {
            webWebView.webViewClient = WebViewClient()
            webWebView.setInitialScale(1)
            webWebView.settings.useWideViewPort = true
            webWebView.settings.loadWithOverviewMode = true
            webWebView.settings.javaScriptEnabled = true
            webWebView.loadUrl("https://www.wikidata.org/wiki/${wikiDataId}")
        }
    }

    private fun backButton(view:View){
        binding.apply {
            webBackButton.setOnClickListener {
                Navigation.findNavController(view).popBackStack()
            }
        }
    }

}

