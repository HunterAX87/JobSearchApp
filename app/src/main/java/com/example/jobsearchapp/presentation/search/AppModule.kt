package com.example.jobsearchapp.presentation.search

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val application: Application) {

    @Provides
    @Singleton
    fun provideContext(): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideDataRepository(context: Context): DataRepository {
        return DataRepository(context)
    }

    @Provides
    @Singleton
    fun provideViewModelFactory(repository: DataRepository): ViewModelFactory {
        return ViewModelFactory(repository)
    }
}