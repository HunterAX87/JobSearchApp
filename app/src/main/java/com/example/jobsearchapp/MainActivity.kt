package com.example.jobsearchapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.jobsearchapp.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Получаем NavHostFragment и NavController
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        // Устанавливаем навигацию для BottomNavigationView
        binding.bNav.setOnNavigationItemSelectedListener { menuItem ->
            val currentDestination = navController.currentDestination?.id

            when (menuItem.itemId) {
                R.id.mainScreen -> {
                    // Проверяем, что мы не на экранах LoginScreen и LoginConfirmScreen
                    if (currentDestination != R.id.loginScreen && currentDestination != R.id.loginConfirmScreen) {
                        navController.navigate(R.id.mainScreen)
                    }
                    true
                }
                R.id.nav_favorites -> {
                    // Проверяем, что мы не на экранах LoginScreen и LoginConfirmScreen
                    if (currentDestination != R.id.loginScreen && currentDestination != R.id.loginConfirmScreen) {
                        navController.navigate(R.id.favoritesFragment)
                    }
                    true
                }
                // Добавьте другие пункты меню, если необходимо
                else -> false
            }
        }

        // Слушатель изменений назначения
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginScreen, R.id.loginConfirmScreen -> {
                    binding.bNav.isClickable= false // Скрываем BottomNavigationView
                }
                else -> {
                    binding.bNav.isClickable= true  // Показываем BottomNavigationView
                }
            }
        }
    }
}