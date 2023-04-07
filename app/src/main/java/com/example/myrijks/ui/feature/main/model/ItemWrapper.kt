package com.example.myrijks.ui.feature.main.model

sealed class ItemWrapper<T>(val item: T, val type: ItemWrapperType) {

    enum class ItemWrapperType {
        ART,
        MAKER
    }

}

class ArtItemWrapper(artViewData: ArtViewData) :
    ItemWrapper<ArtViewData>(artViewData, ItemWrapperType.ART)

class MakerItemWrapper(makerViewData: MakerViewData) :
    ItemWrapper<MakerViewData>(makerViewData, ItemWrapperType.MAKER)
