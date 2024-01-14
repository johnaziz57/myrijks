package com.example.myrijks.domain.repo

import com.example.myrijks.data.model.ArtCollectionResponse
import com.example.myrijks.data.model.ArtObjectDetailsResponse
import io.reactivex.rxjava3.core.Single

interface CollectionRepository {
    suspend fun getCollection(pageIndex: Int): ArtCollectionResponse

    fun getArtObjectDetails(artObjectId: String): Single<ArtObjectDetailsResponse>
}
