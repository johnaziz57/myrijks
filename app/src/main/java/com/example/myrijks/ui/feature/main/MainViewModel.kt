package com.example.myrijks.ui.feature.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myrijks.domain.interactor.CollectionInteractor
import com.example.myrijks.ui.feature.main.model.ArtItemWrapper
import com.example.myrijks.ui.feature.main.model.ArtViewData
import com.example.myrijks.ui.feature.main.model.ItemWrapper
import com.example.myrijks.ui.feature.main.model.MakerItemWrapper
import com.example.myrijks.ui.feature.main.model.MakerViewData
import com.example.myrijks.ui.viewmodel.BaseViewModel
import com.example.myrijks.ui.viewmodel.SchedulerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val collectionInteractor: CollectionInteractor,
    schedulerProvider: SchedulerProvider
) : BaseViewModel(schedulerProvider) {

    val collectionLiveData: LiveData<List<ItemWrapper<*>>>
        get() = _collection

    private val _collection: MutableLiveData<List<ItemWrapper<*>>> = MutableLiveData()

    fun loadCollection() {
        executeWithSchedulers(
            single = collectionInteractor.getArtCollectionByAuthor(),
            onSuccess = { _collection.value = mapToItemWrapper(it) },
            onError = {}
        )
    }

    private fun mapToItemWrapper(artByAuthor: Map<String, List<ArtViewData>>): List<ItemWrapper<*>> {
        return artByAuthor.flatMap { entry ->
            mutableListOf<ItemWrapper<*>>(
                MakerItemWrapper(MakerViewData(entry.key)),
            ).let {
                it.addAll(entry.value.map { ArtItemWrapper(it) })
                it
            }
        }
    }
}