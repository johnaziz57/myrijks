package com.example.myrijks.data.repo

import com.example.myrijks.data.api.RijksService
import com.example.myrijks.data.model.ArtCollectionResponse
import com.example.myrijks.data.model.ArtObjectDetailsResponse
import com.example.myrijks.domain.repo.CollectionRepository
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class CollectionRepositoryImpl @Inject constructor(
    private val rijksService: RijksService
) : CollectionRepository {

    override fun getCollection(pageIndex: Int): Single<ArtCollectionResponse> {
        return rijksService.getCollection(pageIndex = pageIndex)
    }

    override fun getArtObjectDetails(artObjectId: String): Single<ArtObjectDetailsResponse> {
        return rijksService.getArtObjectDetails(artObjectId = artObjectId)
    }
}