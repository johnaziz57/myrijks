package com.example.myrijks.ui.feature.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myrijks.domain.interactor.CollectionInteractor
import com.example.myrijks.domain.model.details.ArtDetailsEntity
import com.example.myrijks.domain.util.Result
import com.example.myrijks.ui.model.ResultStatus
import com.example.myrijks.ui.viewmodel.BaseViewModel
import com.example.myrijks.ui.viewmodel.SchedulerProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val collectionInteractor: CollectionInteractor,
    schedulerProvider: SchedulerProvider
) : BaseViewModel(schedulerProvider) {

    val artDetailsLiveData: LiveData<ArtDetailsEntity>
        get() = _artDetails

    private val _artDetails: MutableLiveData<ArtDetailsEntity> = MutableLiveData()

    val resultStatusLiveData: LiveData<ResultStatus>
        get() = _resultStatus

    private val _resultStatus: MutableLiveData<ResultStatus> = MutableLiveData(ResultStatus.DEFAULT)

    fun getArtObjectDetails(artObjectId: String) {
        execute(
            single = collectionInteractor.getArtObjectDetails(artObjectId),
            onSuccess = {
                when (it) {
                    is Result.Success -> {
                        val x = it.data
                        _artDetails.value = x
                    }

                    is Result.Error -> {
                        _error.value = "error"
                    }
                }

            },
            onError = {}
        )
    }
}