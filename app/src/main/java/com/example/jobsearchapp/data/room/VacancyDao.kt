package com.example.jobsearchapp.data.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


@Dao
interface VacancyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vacancyEntity: VacancyEntity)

    @Update
    suspend fun update(vacancyEntity: VacancyEntity)

    @Query("SELECT * FROM vacancies WHERE isFavorite = 1")
    suspend fun getFavoriteVacancies(): List<VacancyEntity>
}