package com.example.myrijks.data.repo

import com.example.myrijks.data.api.RijksService
import com.example.myrijks.data.model.ArtCollectionResponse
import com.example.myrijks.data.model.ArtObjectDetailsResponse
import com.example.myrijks.domain.repo.CollectionRepository
import javax.inject.Inject

class CollectionRepositoryImpl @Inject constructor(
    private val rijksService: RijksService
) : CollectionRepository {

    override suspend fun getCollection(pageIndex: Int): ArtCollectionResponse {
        return rijksService.getCollection(pageIndex = pageIndex)
    }

    override suspend fun getArtObjectDetails(artObjectId: String): ArtObjectDetailsResponse {
        return rijksService.getArtObjectDetails(artObjectId = artObjectId)
    }
}