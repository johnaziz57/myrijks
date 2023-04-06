package com.example.myrijks.ui.feature.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myrijks.domain.CollectionInteractor
import com.example.myrijks.ui.feature.main.model.ArtViewData
import com.example.myrijks.ui.viewmodel.BaseViewModel
import com.example.myrijks.ui.viewmodel.SchedulerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val collectionInteractor: CollectionInteractor,
    schedulerProvider: SchedulerProvider
) : BaseViewModel(schedulerProvider) {

    val collectionLiveData: LiveData<List<ArtViewData>>
        get() = _collection

    private val _collection: MutableLiveData<List<ArtViewData>> = MutableLiveData()

    fun loadCollection() {
        executeWithSchedulers(
            single = collectionInteractor.getArtCollection(),
            onSuccess = { _collection.value = it },
            onError = {}
        )
    }
}