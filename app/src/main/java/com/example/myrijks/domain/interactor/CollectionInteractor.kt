package com.example.myrijks.domain.interactor

import com.example.myrijks.ui.feature.main.model.ArtViewData
import io.reactivex.rxjava3.core.Single

interface CollectionInteractor {
    fun getArtCollection(): Single<List<ArtViewData>>
}