package com.example.myrijks.data.model
// TODO clean up model
data class ArtObject(
    val links: Links,
    val id: String,
    val objectNumber: String,
    val title: String,
    val hasImage: Boolean,
    val principalOrFirstMaker: String,
    val longTitle: String,
    val showImage: Boolean,
    val permitDownload: Boolean,
    val webImage: Image,
    val headerImage: Image,
    val productionPlaces: List<String>,
)