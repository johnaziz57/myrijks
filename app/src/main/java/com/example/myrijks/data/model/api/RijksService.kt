package com.example.myrijks.data.model.api

import com.example.myrijks.data.model.Collection
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface RijksService {
    @GET("/api/en/collection") // TODO configure culture by language
    fun getCollection(): Single<Collection>
}