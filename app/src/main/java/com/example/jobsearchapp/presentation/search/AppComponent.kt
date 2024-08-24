package com.example.jobsearchapp.presentation.search

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(mainScreen: MainScreen)
}