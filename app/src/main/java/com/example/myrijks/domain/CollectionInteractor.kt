package com.example.myrijks.domain

import com.example.myrijks.ui.main.model.ArtViewData
import io.reactivex.rxjava3.core.Single

interface CollectionInteractor {
    fun getArtCollection(): Single<List<ArtViewData>>
}