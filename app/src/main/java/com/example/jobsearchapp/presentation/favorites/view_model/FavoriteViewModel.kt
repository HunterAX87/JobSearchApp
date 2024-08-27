package com.example.jobsearchapp.presentation.favorites.view_model

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobsearchapp.R
import com.example.jobsearchapp.data.repository.DataRepository
import com.example.jobsearchapp.presentation.search.mapToDomain
import com.example.jobsearchapp.presentation.search.mapToUI
import com.example.jobsearchapp.presentation.search.vacancies_list.VacancyUI
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(private val repository: DataRepository) : ViewModel() {

    private val _favoriteVacancies = MutableLiveData<List<VacancyUI>>()
    val favoriteVacancies: LiveData<List<VacancyUI>> get() = _favoriteVacancies

    fun loadFavoriteVacancies() {
        viewModelScope.launch {
            val favoriteVacancies = repository.getFavoriteVacancies().map { it.mapToUI() }
            _favoriteVacancies.postValue(favoriteVacancies)
        }
    }

    fun saveOrUpdateVacancy(vacancy: VacancyUI) {
        viewModelScope.launch {
            repository.saveOrUpdateVacancy(vacancy.mapToDomain())
        }
    }

    fun getVacancyDeclension(count: Int, resources: Resources): String {
        return resources.getQuantityString(
            R.plurals.vacancies_count,
            count,
            count
        )
    }
}