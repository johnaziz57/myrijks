package com.example.myrijks.domain.mapper

import com.example.myrijks.data.model.ArtObjectDetails
import com.example.myrijks.data.model.Dimension
import com.example.myrijks.data.model.PrincipalMaker
import com.example.myrijks.domain.model.details.ArtDetailsEntity
import javax.inject.Inject

class ArtDetailsMapper @Inject constructor() {
    fun mapToArtDetailsEntity(artObjectDetails: ArtObjectDetails): ArtDetailsEntity {
        return ArtDetailsEntity(
            id = artObjectDetails.id,
            objectNumber = artObjectDetails.objectNumber,
            imageUrl = artObjectDetails.webImage?.url,
            title = artObjectDetails.longTitle ?: "",
            subTitle = artObjectDetails.subTitle ?: "",
            scLabelLine = artObjectDetails.scLabelLine ?: "",
            description = artObjectDetails.description ?: "",
            physicalMedium = artObjectDetails.physicalMedium ?: "",
            dimensionDescription = mapToDimensionDescription(artObjectDetails.dimensions),
            dating = artObjectDetails.dating.presentingDate,
            productionPlacesDescription = mapToProductionPlacesDescription(artObjectDetails.productionPlaces),
            materials = mapToMaterialsDescription(artObjectDetails.materials),
            principalMakers = mapToPrincipalMakers(artObjectDetails.principalMakers)
        )
    }

    private fun mapToDimensionDescription(dimensions: List<Dimension>): String {
        return dimensions.joinToString(separator = ", ") {
            "%s: %s %s".format(
                it.type,
                it.value,
                it.unit
            )
        }
    }

    private fun mapToProductionPlacesDescription(productionPlaces: List<String>): String {
        return productionPlaces.joinToString(separator = ", ") { it }
    }

    private fun mapToMaterialsDescription(marterials: List<String>): String {
        return marterials.joinToString(separator = ", ") { it }
    }

    private fun mapToPrincipalMakers(principalMakers: List<PrincipalMaker>): List<String> {
        return principalMakers.map { "%s (%s)".format(it.name, it.nationality) }
    }
}