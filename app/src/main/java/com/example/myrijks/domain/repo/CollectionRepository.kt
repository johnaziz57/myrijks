package com.example.myrijks.domain.repo

import com.example.myrijks.data.model.ArtCollectionResponse
import com.example.myrijks.data.model.ArtObjectDetailsResponse

interface CollectionRepository {
    suspend fun getCollection(pageIndex: Int): ArtCollectionResponse

    suspend fun getArtObjectDetails(artObjectId: String): ArtObjectDetailsResponse
}
