package com.example.myrijks.domain.interactor

import com.example.myrijks.domain.model.details.ArtDetailsEntity
import com.example.myrijks.domain.model.main.ArtEntity
import com.example.myrijks.domain.model.main.ArtEntityMap
import com.example.myrijks.domain.util.Result
import io.reactivex.rxjava3.core.Single

interface CollectionInteractor {
    suspend fun getArtCollection(): Result<List<ArtEntity>>

    suspend fun getArtCollectionByMaker(pageIndex: Int): Result<ArtEntityMap>

    fun getArtObjectDetails(artObjectId: String): Single<Result<ArtDetailsEntity>>
}