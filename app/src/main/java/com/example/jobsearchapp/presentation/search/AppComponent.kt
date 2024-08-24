package com.example.jobsearchapp.presentation.search

import com.example.jobsearchapp.presentation.favorites.FavoritesFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(mainScreen: MainScreen)
    fun inject(favoritesFragment: FavoritesFragment) // Добавьте этот метод
}