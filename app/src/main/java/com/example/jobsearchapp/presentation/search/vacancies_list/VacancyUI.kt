package com.example.jobsearchapp.presentation.search.vacancies_list

import com.example.jobsearchapp.data.model.Address
import com.example.jobsearchapp.data.model.Experience
import com.example.jobsearchapp.data.model.Salary
import java.io.Serializable

data class VacancyUI(
    val id: String,
    val lookingNumber: Int,
    val title: String,
    val address: Address,
    val company: String,
    val experience: Experience,
    val publishedDate: String,
    var isFavorite: Boolean,
    val salary: Salary,
    val schedules: List<String>,
    val appliedNumber: Int,
    val description: String?,
    val responsibilities: String?,
    val questions: List<String>
) : Serializable