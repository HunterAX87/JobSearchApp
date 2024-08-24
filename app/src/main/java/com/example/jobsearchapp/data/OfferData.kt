package com.example.jobsearchapp.data

data class OffersResponse(
    val offers: List<Offer>,
    val vacancies: List<Vacancy> // Если вам нужны вакансии, создайте соответствующий класс
)

data class Offer(
    val id: String,
    val title: String,
    val link: String,
    val button: Button? // Обязательно добавьте класс Button
)

data class Button(
    val text: String
)

data class Vacancy(
    val id: String,
    val title: String,
    // Добавьте остальные поля, которые вам нужны
)