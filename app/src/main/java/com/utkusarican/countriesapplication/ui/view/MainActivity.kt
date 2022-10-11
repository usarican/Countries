package com.utkusarican.countriesapplication.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.utkusarican.countriesapplication.R
import com.utkusarican.countriesapplication.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bottomNavigationBarNavigate()


    }


    private fun bottomNavigationBarNavigate(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment?
        navHostFragment?.navController?.let { navController ->
            binding.apply {
                appBottomNavigationBar.apply {
                    setupWithNavController(navController)
                    setOnItemSelectedListener {
                        NavigationUI.onNavDestinationSelected(it,navController)
                        navController.popBackStack(it.itemId,false)
                        true
                    }
                }
            }
        }
    }


}