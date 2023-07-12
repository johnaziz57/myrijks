package com.example.myrijks.ui.feature.main.model

import com.example.myrijks.domain.model.main.ArtEntity
import com.example.myrijks.domain.model.main.MakerEntity

sealed class ItemWrapper<T>(val item: T)

class ArtItemWrapper(artEntity: ArtEntity) : ItemWrapper<ArtEntity>(artEntity)

class MakerItemWrapper(makerEntity: MakerEntity) : ItemWrapper<MakerEntity>(makerEntity)
