package com.example.myrijks.ui.feature.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myrijks.domain.interactor.CollectionInteractor
import com.example.myrijks.domain.model.details.ArtDetailsEntity
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


    fun getArtObjectDetails(artObjectId: String) {
        execute(
            single = collectionInteractor.getArtObjectDetails(artObjectId),
            onSuccess = { _artDetails.value = it }
        )
    }
}