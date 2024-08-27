package com.example.jobsearchapp.data.repository

import android.content.Context
import android.util.Log
import com.example.jobsearchapp.data.local.VacancyDao
import com.example.jobsearchapp.data.model.Offer
import com.example.jobsearchapp.data.model.OffersResponse
import com.example.jobsearchapp.data.model.Vacancy
import com.example.jobsearchapp.data.toEntity
import com.example.jobsearchapp.data.toVacancy
import com.google.gson.Gson
import javax.inject.Inject

class DataRepository @Inject constructor(
    private val context: Context,
    private val vacancyDao: VacancyDao
) {

    fun getOffers(): List<Offer> {
        return try {
            val jsonString =
                context.assets.open("mock_json.json").bufferedReader().use { it.readText() }
            val gson = Gson()
            val offersResponse = gson.fromJson(jsonString, OffersResponse::class.java)
            Log.d("MyLog", "Loaded offers from JSON: ${offersResponse.offers.size} items")
            offersResponse.offers
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun getVacancies(): List<Vacancy> {
        return try {
            val jsonString =
                context.assets.open("mock_json.json").bufferedReader().use { it.readText() }
            val gson = Gson()
            val offersResponse = gson.fromJson(jsonString, OffersResponse::class.java)
            val vacanciesFromDb = getFavoriteVacancies()

            offersResponse.vacancies.forEach { vacancy ->
                vacancy.isFavorite =
                    vacanciesFromDb.any { it.id == vacancy.id }
            }

            offersResponse.vacancies
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun saveOrUpdateVacancy(vacancy: Vacancy) {
        val vacancyEntity = vacancy.toEntity()
        vacancyDao.insert(vacancyEntity)
    }

    suspend fun getFavoriteVacancies(): List<Vacancy> {
        return vacancyDao.getFavoriteVacancies().map {
            it.toVacancy()
        }
    }

}