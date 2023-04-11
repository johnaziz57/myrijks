package com.example.myrijks.data.api

import com.example.myrijks.data.model.ArtCollectionResponse
import com.example.myrijks.data.model.ArtObjectDetailsResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RijksService {
    @GET("/api/en/collection")
    fun getCollection(
        @Query("s") sort: String = "artist",
        @Query("p") pageIndex: Int
    ): Single<ArtCollectionResponse>

    @GET("/api/en/collection/{artObjectId}")
    fun getArtObjectDetails(@Path("artObjectId") artObjectId: String): Single<ArtObjectDetailsResponse>
}