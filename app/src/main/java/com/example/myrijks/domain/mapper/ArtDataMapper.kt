package com.example.myrijks.domain.mapper

import com.example.myrijks.data.model.ArtObject
import com.example.myrijks.data.model.Image
import com.example.myrijks.ui.main.model.ArtViewData
import javax.inject.Inject


class ArtDataMapper @Inject constructor() {
    fun mapToArtViewData(artObject: ArtObject): ArtViewData {
        return ArtViewData(
            id = artObject.id,
            title = artObject.title,
            objectNumber = artObject.objectNumber,
            imageUrl = mapToImageUrl(artObject.webImage),
            principalOrFirstMaker = artObject.principalOrFirstMaker
        )
    }

    private fun mapToImageUrl(image: Image): String {
        return image.url
    }
}