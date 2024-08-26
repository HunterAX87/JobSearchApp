package com.example.jobsearchapp.presentation.search

import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobsearchapp.R
import com.example.jobsearchapp.data.model.Vacancy
import com.example.jobsearchapp.data.repository.DataRepository
import com.example.jobsearchapp.presentation.search.ofers_list.OfferUI
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: DataRepository) : ViewModel() {

    private val _offers = MutableLiveData<List<OfferUI>>()
    val offers: LiveData<List<OfferUI>> get() = _offers

    private val _vacancies = MutableLiveData<List<Vacancy>>()
    val vacancies: LiveData<List<Vacancy>> get() = _vacancies

    private val _favoriteVacancies = MutableLiveData<List<Vacancy>>()
    val favoriteVacancies: LiveData<List<Vacancy>> get() = _favoriteVacancies

    fun loadOffers() {
        viewModelScope.launch {
            _offers.value = repository.getOffers().map { it.mapToUI() }
            Log.e("MyLog", "loadedOffers:  ${_offers.value}")
        }
    }

    // todo смаппить так же как и с loadOffers
    fun loadVacancies() {
        viewModelScope.launch {
            _vacancies.value = repository.getVacancies()
        }
    }

    fun loadFavoriteVacancies() {
        viewModelScope.launch {
            val favoriteVacancies = repository.getFavoriteVacancies()
            _favoriteVacancies.postValue(favoriteVacancies)
        }
    }


    fun saveOrUpdateVacancy(vacancy: Vacancy) {
        viewModelScope.launch {
            repository.saveOrUpdateVacancy(vacancy)
        }
    }

    fun getVacancyDeclension(count: Int, resources: Resources): String {
        return resources.getQuantityString(
            R.plurals.vacancies_count,
            count,
            count,
            when {
                count % 10 == 1 && count % 100 != 11 -> "one"
                count % 10 in 2..4 && (count % 100 < 10 || count % 100 > 20) -> "few"
                else -> "many"
            }
        )
    }


}