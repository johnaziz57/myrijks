package com.example.myrijks.data.model

data class ArtObjectDetailsResponse(
    val elapsedMilliseconds: Long,
    val artObject: ArtObjectDetails,
)

data class ArtObjectDetails(
    val id: String,
    val objectNumber: String,
    val webImage: Image?,
    val colors: List<Color>,
    val description: String?,
    val objectTypes: List<String>,
    val principalMakers: List<PrincipalMaker>,
    val plaqueDescriptionEnglish: String?,
    val materials: List<String>,
    val productionPlaces: List<String>,
    val dating: Dating,
    val dimensions: List<Dimension>,
    val physicalMedium: String?,
    val longTitle: String?,
    val subTitle: String?,
    val scLabelLine: String?,
)

data class Color(
    val percentage: Long,
    val hex: String,
)

data class PrincipalMaker(
    val name: String,
    val unFixedName: String,
    val placeOfBirth: String?,
    val dateOfBirth: String?,
    val dateOfBirthPrecision: Any?,
    val dateOfDeath: String?,
    val dateOfDeathPrecision: Any?,
    val placeOfDeath: String?,
    val occupation: List<String>,
    val roles: List<String>,
    val nationality: String?,
    val productionPlaces: List<String>,
)

data class Dating(
    val presentingDate: String,
    val sortingDate: Long,
    val period: Long,
    val yearEarly: Long,
    val yearLate: Long,
)

data class Dimension(
    val unit: String,
    val type: String,
    val value: String,
)
