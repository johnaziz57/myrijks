package com.example.myrijks.data.api

import com.example.myrijks.data.model.ArtCollection
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface RijksService {
    // TODO configure culture by language
    // TODO configure sorting
    @GET("/api/en/collection")
    fun getCollection(@Query("s") sort: String = "artist"): Single<ArtCollection>
}