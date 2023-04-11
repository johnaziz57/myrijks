package com.example.myrijks.ui.feature.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myrijks.domain.interactor.CollectionInteractor
import com.example.myrijks.ui.feature.details.model.ArtDetailsViewData
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

    val artDetailsLiveData: LiveData<ArtDetailsViewData>
        get() = _artDetails

    private val _artDetails: MutableLiveData<ArtDetailsViewData> = MutableLiveData()

    val resultStatusLiveData: LiveData<ResultStatus>
        get() = _resultStatus

    private val _resultStatus: MutableLiveData<ResultStatus> = MutableLiveData(ResultStatus.DEFAULT)

    fun getArtObjectDetails(artObjectId: String) {
        execute(
            single = collectionInteractor.getArtObjectDetails(artObjectId),
            onSuccess = { _artDetails.value = it },
            onError = {},
            resultStatusLiveData = _resultStatus
        )
    }
}