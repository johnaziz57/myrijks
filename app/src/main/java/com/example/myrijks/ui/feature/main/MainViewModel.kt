package com.example.myrijks.ui.feature.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myrijks.domain.interactor.CollectionInteractor
import com.example.myrijks.domain.model.error.ErrorEntity
import com.example.myrijks.domain.model.main.ArtEntityMap
import com.example.myrijks.domain.model.main.MakerEntity
import com.example.myrijks.domain.model.main.MutableArtEntityMap
import com.example.myrijks.domain.util.Result
import com.example.myrijks.ui.feature.main.model.ArtItemWrapper
import com.example.myrijks.ui.feature.main.model.ItemWrapper
import com.example.myrijks.ui.feature.main.model.MakerItemWrapper
import com.example.myrijks.ui.model.ResultStatus
import com.example.myrijks.ui.viewmodel.BaseViewModel
import com.example.myrijks.ui.viewmodel.SchedulerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.disposables.Disposable
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val collectionInteractor: CollectionInteractor,
    schedulerProvider: SchedulerProvider
) : BaseViewModel(schedulerProvider) {

    val collectionLiveData: LiveData<List<ItemWrapper<*>>>
        get() = _collection

    val resultStatusLiveData: LiveData<ResultStatus>
        get() = _resultStatus

    private val _collection: MutableLiveData<List<ItemWrapper<*>>> = MutableLiveData()
    private val _resultStatus: MutableLiveData<ResultStatus> = MutableLiveData(ResultStatus.DEFAULT)

    private val collectionMap: MutableArtEntityMap = mutableMapOf()
    private var pageIndex = 1
    private var collectionDisposable: Disposable? = null

    fun loadNextCollection() {
        if (collectionDisposable?.isDisposed?.not() == true) return
        _resultStatus.value = ResultStatus.LOADING
        collectionDisposable = execute(
            single = collectionInteractor.getArtCollectionByMaker(pageIndex),
            onSuccess = {
                when (it) {
                    is Result.Success -> {
                        _resultStatus.value = ResultStatus.SUCCESS
                        val collection = mapToItemWrapper(it.data, collectionMap)
                        synchronizeCollectionMap(it.data, collectionMap)
                        val newCollection = mutableListOf<ItemWrapper<*>>()
                        newCollection.addAll(_collection.value ?: emptyList())
                        newCollection.addAll(collection)
                        _collection.value = newCollection
                        pageIndex++
                    }

                    is Result.Error -> {
                        _resultStatus.value = ResultStatus.ERROR
                        when (it.error) {
                            // TODO use resource string
                            ErrorEntity.Network -> _error.value = "No network error"
                            ErrorEntity.AccessDenied -> _error.value = "Access denied"
                            ErrorEntity.ServiceUnavailable -> _error.value = "Service unavailable"
                            else -> _error.value = "Unknown error occured"
                        }
                    }
                }

            },
            onError = {
                _resultStatus.value = ResultStatus.ERROR
            }
        )
    }

    private fun mapToItemWrapper(
        newCollectionMap: ArtEntityMap,
        currentCollectionMap: ArtEntityMap
    ): List<ItemWrapper<*>> {
        return newCollectionMap.flatMap { entry ->
            val result = entry.value.map { ArtItemWrapper(it) }.toMutableList<ItemWrapper<*>>()
            if (currentCollectionMap.containsKey(entry.key).not()) {
                result.add(0, MakerItemWrapper(MakerEntity(entry.key)))
            }
            result
        }
    }

    private fun synchronizeCollectionMap(
        newCollectionMap: ArtEntityMap,
        currentCollectionMap: MutableArtEntityMap
    ) {
        newCollectionMap.entries.forEach { entry ->
            if (currentCollectionMap.containsKey(entry.key)) {
                val collectionList =
                    currentCollectionMap[entry.key]?.toMutableList() ?: mutableListOf()
                collectionList.addAll(entry.value)
                currentCollectionMap[entry.key] = collectionList.toList()
            } else {
                currentCollectionMap[entry.key] = entry.value
            }
        }
    }
}