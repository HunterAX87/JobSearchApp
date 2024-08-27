package com.example.jobsearchapp.presentation.search.view_model

import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobsearchapp.R
import com.example.jobsearchapp.data.repository.DataRepository
import com.example.jobsearchapp.presentation.search.mapToDomain
import com.example.jobsearchapp.presentation.search.mapToUI
import com.example.jobsearchapp.presentation.search.ofers_list.OfferUI
import com.example.jobsearchapp.presentation.search.vacancies_list.VacancyUI
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchMainViewModel @Inject constructor(private val repository: DataRepository) : ViewModel() {

    private val _offers = MutableLiveData<List<OfferUI>>()
    val offers: LiveData<List<OfferUI>> get() = _offers

    private val _vacancies = MutableLiveData<List<VacancyUI>>()
    val vacancies: LiveData<List<VacancyUI>> get() = _vacancies

    private val _vacanciesCount = MutableLiveData<Int>()
    val vacanciesCount: LiveData<Int> get() = _vacanciesCount

    fun loadOffers() {
        viewModelScope.launch {
            _offers.value = repository.getOffers().map { it.mapToUI() }
        }
    }

    fun loadVacancies(resources: Resources) {
        viewModelScope.launch {
            val vacanciesList = repository.getVacancies().map { it.mapToUI() }
            _vacancies.value = vacanciesList
            _vacanciesCount.value = vacanciesList.size
        }
    }

    fun loadFirst3Vacancies(resources: Resources) {
        viewModelScope.launch {
            val vacanciesList = repository.getVacancies().map { it.mapToUI() }
            val first3Vacancies = vacanciesList.take(3)
            _vacancies.value = first3Vacancies
            _vacanciesCount.value = first3Vacancies.size
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