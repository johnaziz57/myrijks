package com.example.myrijks.domain.interactor

import com.example.myrijks.domain.model.details.ArtDetailsEntity
import com.example.myrijks.domain.model.main.ArtEntity
import com.example.myrijks.domain.model.main.ArtEntityMap
import com.example.myrijks.domain.util.Result

interface CollectionInteractor {
    suspend fun getArtCollection(): Result<List<ArtEntity>>

    suspend fun getArtCollectionByMaker(pageIndex: Int): Result<ArtEntityMap>

    suspend fun getArtObjectDetails(artObjectId: String): Result<ArtDetailsEntity>
}