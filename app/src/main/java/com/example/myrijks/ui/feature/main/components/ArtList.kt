package com.example.myrijks.ui.feature.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myrijks.ui.feature.main.model.ArtItemWrapper
import com.example.myrijks.ui.feature.main.model.ArtViewData
import com.example.myrijks.ui.feature.main.model.ItemWrapper
import com.example.myrijks.ui.feature.main.model.MakerItemWrapper
import com.example.myrijks.ui.feature.main.model.MakerViewData

@Composable
fun ArtList(items: List<ItemWrapper<*>>, onArtClicked: (artObjectId: String) -> Unit) {
    val state = rememberLazyListState()
    LazyColumn(
        contentPadding = PaddingValues(all = 4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        state = state
    ) {
        items(items) {
            when (it) {
                is ArtItemWrapper -> ArtView(artViewData = it.item, onArtClicked = onArtClicked)
                is MakerItemWrapper -> MakerView(makerViewData = it.item)
            }
        }
    }
}

@Preview
@Composable
fun ArtListPreview() {
    val list = listOf<ItemWrapper<*>>(
        MakerItemWrapper(makerViewData = MakerViewData("Jack")),
        ArtItemWrapper(
            artViewData = ArtViewData(
                "Id",
                title = "The scream",
                principalOrFirstMaker = "Leo",
                objectNumber = "123",
                imageUrl = "www.imagenotfound.com"
            )
        )
    )

    ArtList(items = list, onArtClicked = {})
}