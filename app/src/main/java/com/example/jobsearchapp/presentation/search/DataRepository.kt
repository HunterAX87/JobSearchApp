package com.example.jobsearchapp.presentation.search

import android.content.Context
import android.util.Log
import com.example.jobsearchapp.data.Offer
import com.example.jobsearchapp.data.OffersResponse
import com.example.jobsearchapp.data.Vacancy
import com.example.jobsearchapp.data.room.VacancyDao
import com.example.jobsearchapp.data.room.VacancyEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val context: Context,
    private val vacancyDao: VacancyDao
) {

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

    fun getVacancies(): List<Vacancy> {
        return try {
            val jsonString = context.assets.open("mock_json.json").bufferedReader().use { it.readText() }
            val gson = Gson()
            val offersResponse = gson.fromJson(jsonString, OffersResponse::class.java)
            Log.d("DataRepository", "Loaded vacancies from JSON: ${offersResponse.vacancies.size} items")
            offersResponse.vacancies // Возвращаем список vacancies
        } catch (e: Exception) {
            Log.e("DataRepository", "Error loading vacancies: ${e.message}")
            e.printStackTrace() // Логирование ошибки
            emptyList() // Возвращаем пустой список в случае ошибки
        }
    }

    suspend fun saveOrUpdateVacancy(vacancyEntity: VacancyEntity) {
        vacancyDao.insert(vacancyEntity)
    }


        // Получение избранных вакансий в корутине
        suspend fun getFavoriteVacancies(): List<VacancyEntity> {
            return vacancyDao.getFavoriteVacancies() // Убедитесь, что этот метод возвращает List<VacancyEntity>
        }

}