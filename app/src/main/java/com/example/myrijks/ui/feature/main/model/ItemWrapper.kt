package com.example.myrijks.ui.feature.main.model

import com.example.myrijks.domain.model.main.ArtEntity
import com.example.myrijks.domain.model.main.MakerEntity

sealed class ItemWrapper<T>(val item: T, val type: ItemWrapperType) {

    enum class ItemWrapperType {
        ART,
        MAKER
    }

}

class ArtItemWrapper(artEntity: ArtEntity) :
    ItemWrapper<ArtEntity>(artEntity, ItemWrapperType.ART)

class MakerItemWrapper(makerEntity: MakerEntity) :
    ItemWrapper<MakerEntity>(makerEntity, ItemWrapperType.MAKER)
