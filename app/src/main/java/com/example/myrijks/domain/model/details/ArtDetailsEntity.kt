package com.example.myrijks.domain.model.details

data class ArtDetailsEntity(
    val id: String,
    val objectNumber: String,
    val imageUrl: String?,
    val title: String,
    val subTitle: String,
    val scLabelLine: String,
    val description: String = "",
    val physicalMedium: String,
    val dimensionDescription: String,
    val dating: String,
    val productionPlacesDescription: String,
    val materials: String,
    val principalMakers: List<String>
)