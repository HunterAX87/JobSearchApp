package com.example.jobsearchapp.presentation.search

import com.example.jobsearchapp.R
import com.example.jobsearchapp.data.model.Offer
import com.example.jobsearchapp.data.model.Vacancy
import com.example.jobsearchapp.presentation.search.ofers_list.OfferUI
import com.example.jobsearchapp.presentation.search.vacancies_list.VacancyUI

fun Offer.mapToUI(): OfferUI {

    val safeId = id ?: "unknown_id"

    val iconRes = when (safeId) {
        "near_vacancies" -> R.drawable.geo100
        "level_up_resume" -> R.drawable.star2x
        "temporary_job" -> R.drawable.temp_job2x
        else -> 0
    }

    return OfferUI(
        safeId,
        iconRes,
        title,
        link,
        button?.text ?: ""
    )
}

fun Vacancy.mapToUI(): VacancyUI {
    return VacancyUI(
        id,
        lookingNumber,
        title,
        address,
        company,
        experience,
        publishedDate,
        isFavorite,
        salary,
        schedules,
        appliedNumber,
        description,
        responsibilities,
        questions
    )
}

fun VacancyUI.mapToDomain(): Vacancy {
    return Vacancy(
        id,
        lookingNumber,
        title,
        address,
        company,
        experience,
        publishedDate,
        isFavorite,
        salary,
        schedules,
        appliedNumber,
        description,
        responsibilities,
        questions
    )
}
