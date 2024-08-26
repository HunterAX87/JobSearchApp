package com.example.jobsearchapp

import android.app.Application

import com.example.jobsearchapp.di.AppComponent
import com.example.jobsearchapp.di.AppModule
import com.example.jobsearchapp.di.DaggerAppComponent

class MyApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this)) // Передаем контекст приложения
            .build()
    }
}