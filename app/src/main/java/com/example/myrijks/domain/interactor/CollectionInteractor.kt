package com.example.myrijks.domain.interactor

import com.example.myrijks.ui.feature.details.model.ArtDetailsViewData
import com.example.myrijks.ui.feature.main.model.ArtViewData
import io.reactivex.rxjava3.core.Single

interface CollectionInteractor {
    fun getArtCollection(): Single<List<ArtViewData>>

    fun getArtCollectionByMaker(pageIndex: Int): Single<Map<String, List<ArtViewData>>>

    fun getArtObjectDetails(artObjectId: String): Single<ArtDetailsViewData>
}