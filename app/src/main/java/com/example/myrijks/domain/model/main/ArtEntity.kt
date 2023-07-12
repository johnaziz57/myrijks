package com.example.myrijks.domain.model.main

data class ArtEntity(
    val id: String,
    val title: String,
    val objectNumber: String,
    val imageUrl: String?,
    val principalOrFirstMaker: String
)