package com.example.jobsearchapp.presentation.search.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.jobsearchapp.data.repository.DataRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchViewModelFactory @Inject constructor(
    private val repository: DataRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchMainViewModel::class.java)) {
            return SearchMainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}