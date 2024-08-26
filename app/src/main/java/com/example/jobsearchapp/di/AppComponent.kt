package com.example.jobsearchapp.di

import com.example.jobsearchapp.presentation.about.AboutFragment
import com.example.jobsearchapp.presentation.favorites.FavoritesFragment
import com.example.jobsearchapp.presentation.search.MainScreen
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(mainScreen: MainScreen)
    fun inject(favoritesFragment: FavoritesFragment)
    fun inject(aboutFragment: AboutFragment)
}