package com.example.jobsearchapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.jobsearchapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val controller = findNavController(R.id.fragmentContainerView)
//        setupWithNavController(binding.bNav, findNavController(R.id.fragmentContainerView))
//        controller.addOnDestinationChangedListener { _, destination, _ ->
//            when (destination.id) {
//                R.id.mainScreen -> {
//                    controller.navigate(R.id.mainScreen)
//                    true
//                }
//                R.id.nav_favorites -> {
//       //             controller.navigate(R.id.favoritesFragment)
//                    true
//                }
//            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}


