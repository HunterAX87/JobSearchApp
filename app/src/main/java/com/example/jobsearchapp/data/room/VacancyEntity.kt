package com.example.jobsearchapp.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vacancies")
data class VacancyEntity(
    @PrimaryKey
    val id: String,
    val lookingNumber: Int,
    val title: String,
    val address: String,
    val company: String,
    val experience: String,
    val publishedDate: String,
    var isFavorite: Boolean,
    val salary: String,
    val schedules: String,
    val appliedNumber: Int,
    val description: String,
    val responsibilities: String,
    val questions: String
)