package com.example.myrijks.data.repo

import com.example.myrijks.data.model.ArtCollectionResponse
import com.example.myrijks.data.model.ArtObjectDetailsResponse
import io.reactivex.rxjava3.core.Single

interface CollectionRepository {
    fun getCollection(pageIndex: Int): Single<ArtCollectionResponse>

    fun getArtObjectDetails(artObjectId: String): Single<ArtObjectDetailsResponse>
}
