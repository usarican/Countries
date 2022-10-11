package com.utkusarican.countriesapplication.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.utkusarican.countriesapplication.data.local.entity.SavedCountry
import com.utkusarican.countriesapplication.data.remote.response.Country
import com.utkusarican.countriesapplication.databinding.CountryItemBinding
import com.utkusarican.countriesapplication.ui.view.CountriesFragmentDirections
import com.utkusarican.countriesapplication.ui.view.SavedFragmentDirections
import javax.inject.Inject

class SavedCountriesAdapter @Inject constructor(
) : ListAdapter<SavedCountry,SavedCountriesAdapter.SavedCountriesViewHolder>(SAVED_COUNTRY_COMPARATOR) {

    private lateinit var myListener: onItemClickListener

    interface onItemClickListener{
        fun onItemClicked(savedCountry: SavedCountry)
    }

    class SavedCountriesViewHolder(private val binding : CountryItemBinding,private val listener: onItemClickListener) : RecyclerView.ViewHolder(binding.root) {
        fun bind(savedCountry: SavedCountry) {
            binding.apply {
                countryName.text = savedCountry.countryName
                savedIcon.isChecked = true
                savedIcon.setOnClickListener {
                    listener.onItemClicked(savedCountry)
                }
                itemView.setOnClickListener {
                   val action = SavedFragmentDirections.actionSavedFragmentToCountryDetailFragment3(savedCountry.countryCode)
                    Navigation.findNavController(it).navigate(action)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedCountriesViewHolder {
        val binding = CountryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return SavedCountriesViewHolder(binding,myListener)
    }

    override fun onBindViewHolder(holder: SavedCountriesViewHolder, position: Int) {
        val currentItem = getItem(position)
        currentItem?.let {
            holder.bind(currentItem)
        }

    }

    fun setOnItemClickListener(listener: SavedCountriesAdapter.onItemClickListener){
        myListener = listener
    }

    companion object {
        private val SAVED_COUNTRY_COMPARATOR = object : DiffUtil.ItemCallback<SavedCountry>(){
            override fun areItemsTheSame(oldItem: SavedCountry, newItem: SavedCountry): Boolean {
                return oldItem.countryCode == newItem.countryCode
            }

            override fun areContentsTheSame(oldItem: SavedCountry, newItem: SavedCountry): Boolean {
                return oldItem.countryCode == newItem.countryCode
            }

        }
    }
}