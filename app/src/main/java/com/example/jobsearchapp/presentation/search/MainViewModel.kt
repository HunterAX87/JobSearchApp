package com.example.jobsearchapp.presentation.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.jobsearchapp.data.Offer

class MainViewModel(private val repository: DataRepository) : ViewModel() {

    private val _offers = MutableLiveData<List<Offer>>()
    val offers: LiveData<List<Offer>> get() = _offers

    fun loadOffers() {
        val offersList = repository.getOffers()
        _offers.value = offersList
        Log.e("MyLog", "Loaded offers: ${offersList.size} items")
    }
}