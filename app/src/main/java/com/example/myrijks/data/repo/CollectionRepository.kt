package com.example.myrijks.data.repo

import com.example.myrijks.data.model.ArtCollection
import io.reactivex.rxjava3.core.Single

interface CollectionRepository {
    fun getCollection(pageIndex: Int): Single<ArtCollection>
}
