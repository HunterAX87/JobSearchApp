package com.example.jobsearchapp

import android.app.Application

import com.example.jobsearchapp.presentation.search.AppComponent
import com.example.jobsearchapp.presentation.search.DaggerAppComponent
import com.example.jobsearchapp.presentation.search.AppModule

class MyApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this)) // Передаем контекст приложения
            .build()
    }
}