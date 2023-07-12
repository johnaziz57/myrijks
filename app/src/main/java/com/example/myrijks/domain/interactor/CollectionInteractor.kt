package com.example.myrijks.domain.interactor

import com.example.myrijks.domain.util.Result
import com.example.myrijks.ui.feature.details.model.ArtDetailsViewData
import com.example.myrijks.ui.feature.main.model.ArtDataMap
import com.example.myrijks.ui.feature.main.model.ArtViewData
import io.reactivex.rxjava3.core.Single

interface CollectionInteractor {
    fun getArtCollection(): Single<Result<List<ArtViewData>>>

    fun getArtCollectionByMaker(pageIndex: Int): Single<Result<ArtDataMap>>

    fun getArtObjectDetails(artObjectId: String): Single<Result<ArtDetailsViewData>>
}