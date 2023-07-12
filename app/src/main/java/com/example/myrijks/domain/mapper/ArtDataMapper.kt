package com.example.myrijks.domain.mapper

import com.example.myrijks.data.model.ArtObject
import com.example.myrijks.domain.model.main.ArtEntity
import javax.inject.Inject


class ArtDataMapper @Inject constructor() {
    fun mapToArtEntity(artObject: ArtObject): ArtEntity {
        return ArtEntity(
            id = artObject.id,
            title = artObject.title,
            objectNumber = artObject.objectNumber,
            imageUrl = artObject.webImage?.url,
            principalOrFirstMaker = artObject.principalOrFirstMaker
        )
    }
}