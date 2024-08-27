package com.example.jobsearchapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.jobsearchapp.data.local.AppDatabase
import com.example.jobsearchapp.data.local.VacancyDao
import com.example.jobsearchapp.data.repository.DataRepository
import com.example.jobsearchapp.presentation.search.view_model.SearchViewModelFactory
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
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java, "vacancy_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideVacancyDao(database: AppDatabase): VacancyDao {
        return database.vacancyDao()
    }

    @Provides
    @Singleton
    fun provideDataRepository(context: Context, vacancyDao: VacancyDao): DataRepository {
        return DataRepository(context, vacancyDao)
    }

    @Provides
    @Singleton
    fun provideViewModelFactory(repository: DataRepository): SearchViewModelFactory {
        return SearchViewModelFactory(repository)
    }
}