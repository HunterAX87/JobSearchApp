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
    val description: String,
    val responsibilities: String,
    val questions: List<String>
)

data class Address(
    val town: String,
    val street: String,
    val house: String
)

data class Experience(
    val previewText: String,
    val text: String
)

data class Salary(
    val short: String?,
    val full: String
)