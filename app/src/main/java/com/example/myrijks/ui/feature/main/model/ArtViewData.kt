package com.example.myrijks.ui.feature.main.model

// TODO move view data to domain
data class ArtViewData(
    val id: String,
    val title: String,
    val objectNumber: String,
    val imageUrl: String?,
    val principalOrFirstMaker: String
)