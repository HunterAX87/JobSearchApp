package com.example.jobsearchapp.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.jobsearchapp.R
import com.example.jobsearchapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController

        itemSelectedListener()
        destinationChangedListener()
    }

    private fun itemSelectedListener() {
        binding.bNav.setOnNavigationItemSelectedListener { menuItem ->
            val currentDestination = navController.currentDestination?.id
            when (menuItem.itemId) {
                R.id.mainScreen -> {
                    if (currentDestination != R.id.loginScreen && currentDestination != R.id.loginConfirmScreen) {
                        navController.navigate(R.id.mainScreen)
                    }
                    true
                }

                R.id.nav_favorites -> {
                    if (currentDestination != R.id.loginScreen && currentDestination != R.id.loginConfirmScreen) {
                        navController.navigate(R.id.favoritesFragment)
                    }
                    true
                }
                else -> false
            }
        }
    }

    private fun destinationChangedListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginScreen, R.id.loginConfirmScreen -> {
                    binding.bNav.isClickable = false
                }
                else -> {
                    binding.bNav.isClickable = true
                }
            }
        }
    }
}