package com.example.myrijks.data.model

data class ArtCollectionResponse(
    val count: Long,
    val artObjects: List<ArtObject>,
)

data class ArtObject(
    val id: String,
    val objectNumber: String,
    val title: String,
    val principalOrFirstMaker: String,
    val longTitle: String,
    val webImage: Image?,
    val headerImage: Image?,
    val productionPlaces: List<String>,
)
