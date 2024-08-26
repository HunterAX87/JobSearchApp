package com.example.jobsearchapp.data.repository

import android.content.Context
import android.util.Log
import com.example.jobsearchapp.data.local.VacancyDao
import com.example.jobsearchapp.data.model.Offer
import com.example.jobsearchapp.data.model.OffersResponse
import com.example.jobsearchapp.data.model.Vacancy
import com.example.jobsearchapp.data.local.VacancyEntity
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
            val jsonString = context.assets.open("mock_json.json").bufferedReader().use { it.readText() }
            val gson = Gson()
            val offersResponse = gson.fromJson(jsonString, OffersResponse::class.java)
            Log.d("MyLog", "Loaded offers from JSON: ${offersResponse.offers.size} items")
            offersResponse.offers // Возвращаем список offers
        } catch (e: Exception) {
            Log.e("MyLog", "Error loading offers: ${e.message}")
            emptyList() // Возвращаем пустой список в случае ошибки
        }
    }


    suspend fun getVacancies(): List<Vacancy> {
        return try {
            val jsonString =
                context.assets.open("mock_json.json").bufferedReader().use { it.readText() }
            val gson = Gson()
            val offersResponse = gson.fromJson(jsonString, OffersResponse::class.java)
            val vacanciesFromDb =
                getFavoriteVacancies() // Получаем избранные вакансии из базы данных

            // Обновляем состояние isFavorite для загруженных вакансий
            offersResponse.vacancies.forEach { vacancy ->
                vacancy.isFavorite =
                    vacanciesFromDb.any { it.id == vacancy.id } // Проверяем, есть ли вакансия в избранном
            }

            Log.d(
                "DataRepository",
                "Loaded vacancies from JSON: ${offersResponse.vacancies.size} items"
            )
            offersResponse.vacancies // Возвращаем список vacancies
        } catch (e: Exception) {
            Log.e("DataRepository", "Error loading vacancies: ${e.message}")
            e.printStackTrace() // Логирование ошибки
            emptyList() // Возвращаем пустой список в случае ошибки
        }
    }

    // toDo вакансию надо смаппить в энтити маппер для того чтоб сделать инссерт
    suspend fun saveOrUpdateVacancy(vacancy: Vacancy) {
        val vacancyEntity = vacancy.toEntity()
        vacancyDao.insert(vacancyEntity)
    }


    // Получение избранных вакансий в корутине
    suspend fun getFavoriteVacancies(): List<Vacancy> {
        return vacancyDao.getFavoriteVacancies().map {
            it.toVacancy()
        }
    }

}