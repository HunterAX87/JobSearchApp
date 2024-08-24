package com.example.jobsearchapp.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jobsearchapp.data.Address
import com.example.jobsearchapp.data.Experience
import com.example.jobsearchapp.data.Offer
import com.example.jobsearchapp.data.Salary
import com.example.jobsearchapp.data.Vacancy
import com.example.jobsearchapp.data.room.VacancyEntity
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: DataRepository) : ViewModel() {

    private val _offers = MutableLiveData<List<Offer>>()
    val offers: LiveData<List<Offer>> get() = _offers

    private val _vacancies = MutableLiveData<List<Vacancy>>()
    val vacancies: LiveData<List<Vacancy>> get() = _vacancies

    private val _favoriteVacancies = MutableLiveData<List<Vacancy>>()
    val favoriteVacancies: LiveData<List<Vacancy>> get() = _favoriteVacancies

    fun loadOffers() {
        viewModelScope.launch {
            _offers.value = repository.getOffers()
        }
    }

    fun loadVacancies() {
        viewModelScope.launch {
            _vacancies.value = repository.getVacancies() // Метод для получения вакансий
        }
    }

    fun loadFavoriteVacancies() {
        viewModelScope.launch {
            // Получаем избранные вакансии из репозитория
            val favoriteVacanciesEntities = repository.getFavoriteVacancies()
            val favoriteVacancies = favoriteVacanciesEntities.map { it.toVacancy() } // Преобразуем VacancyEntity в Vacancy
            _favoriteVacancies.postValue(favoriteVacancies) // Обновляем LiveData
        }
    }

    fun saveOrUpdateVacancy(vacancy: Vacancy) {
        viewModelScope.launch {
            val vacancyEntity = vacancy.toEntity()
            repository.saveOrUpdateVacancy(vacancyEntity)
        }
    }

    private fun Vacancy.toEntity(): VacancyEntity {
        return VacancyEntity(
            id = this.id,
            lookingNumber = this.lookingNumber,
            title = this.title,
            address = this.address.town,
            company = this.company,
            experience = this.experience.previewText,
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

    private fun VacancyEntity.toVacancy(): Vacancy {
        return Vacancy(
            id = this.id,
            lookingNumber = this.lookingNumber,
            title = this.title,
            address = Address(town = this.address, "", ""),
            company = this.company,
            experience = Experience(previewText = this.experience, ""),
            publishedDate = this.publishedDate,
            isFavorite = this.isFavorite,
            salary = Salary(short = this.salary, ""),
            schedules = this.schedules.split(", ").toList(),
            appliedNumber = this.appliedNumber,
            description = this.description ?: "",
            responsibilities = this.responsibilities ?: "",
            questions = this.questions.split(", ").toList()
        )
    }
}