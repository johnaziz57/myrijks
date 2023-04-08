package com.example.myrijks.data.repo

import com.example.myrijks.data.api.RijksService
import com.example.myrijks.data.model.ArtCollection
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class CollectionRepositoryImpl @Inject constructor(
    private val rijksService: RijksService
) : CollectionRepository {

    override fun getCollection(pageIndex: Int): Single<ArtCollection> {
        return rijksService.getCollection(pageIndex = pageIndex)
    }
}