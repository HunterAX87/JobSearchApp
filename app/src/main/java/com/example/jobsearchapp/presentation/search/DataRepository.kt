package com.example.jobsearchapp.presentation.search

import android.content.Context
import android.util.Log
import com.example.jobsearchapp.data.Offer
import com.example.jobsearchapp.data.OffersResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class DataRepository(private val context: Context) {

    fun getOffers(): List<Offer> {
        return try {
            val jsonString = context.assets.open("mock_json.json").bufferedReader().use { it.readText() }
            val gson = Gson()
            val offersResponse = gson.fromJson(jsonString, OffersResponse::class.java)
            Log.d("DataRepository", "Loaded offers from JSON: ${offersResponse.offers.size} items")
            offersResponse.offers // Возвращаем список offers
        } catch (e: Exception) {
            Log.e("DataRepository", "Error loading offers: ${e.message}")
            e.printStackTrace() // Логирование ошибки
            emptyList() // Возвращаем пустой список в случае ошибки
        }
    }
}