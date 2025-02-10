package com.example.myrijks.data.api

import com.example.myrijks.data.model.ArtCollectionResponse
import com.example.myrijks.data.model.ArtObjectDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RijksService {
    @GET("/api/en/collection")
    suspend fun getCollection(
        @Query("s") sort: String = "artist",
        @Query("p") pageIndex: Int
    ): ArtCollectionResponse

    @GET("/api/en/collection/{artObjectId}")
    suspend fun getArtObjectDetails(@Path("artObjectId") artObjectId: String): ArtObjectDetailsResponse
}