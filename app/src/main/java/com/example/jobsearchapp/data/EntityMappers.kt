package com.example.jobsearchapp.data

import com.example.jobsearchapp.data.local.VacancyEntity
import com.example.jobsearchapp.data.model.Address
import com.example.jobsearchapp.data.model.Experience
import com.example.jobsearchapp.data.model.Salary
import com.example.jobsearchapp.data.model.Vacancy

 fun Vacancy.toEntity(): VacancyEntity {
    return VacancyEntity(
        id = this.id,
        lookingNumber = this.lookingNumber,
        title = this.title,
        address = this.address?.town ?: "",
        company = this.company,
        experience = this.experience?.previewText ?: "",
        publishedDate = this.publishedDate,
        isFavorite = this.isFavorite,
        salary = this.salary.short ?: "",
        schedules = this.schedules.joinToString(", "),
        appliedNumber = this.appliedNumber,
        description = this.description ?: "",
        responsibilities = this.responsibilities ?: "",
        questions = this.questions.joinToString(", ")
    )
}

 fun VacancyEntity.toVacancy(): Vacancy {
    return Vacancy(
        id = this.id,
        lookingNumber = this.lookingNumber,
        title = this.title,
        address = Address(town = this.address, "", ""),
        company = this.company,
        experience = Experience(
            previewText = this.experience,
            fullText = ""
        ),
        publishedDate = this.publishedDate,
        isFavorite = this.isFavorite,
        salary = Salary(short = this.salary, full = ""),
        schedules = this.schedules.split(", ").toList(),
        appliedNumber = this.appliedNumber,
        description = this.description ?: "",
        responsibilities = this.responsibilities ?: "",
        questions = this.questions.split(", ").toList()
    )
}