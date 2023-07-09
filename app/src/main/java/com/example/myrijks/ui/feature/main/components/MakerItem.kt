package com.example.myrijks.ui.feature.main.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myrijks.ui.feature.main.model.MakerViewData


@Composable
fun MakerView(
    makerViewData: MakerViewData
) {
    Text(
        text = makerViewData.maker,
        modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp)
    )
}

@Preview
@Composable
fun MakerItemPreview() {
    MakerView(makerViewData = MakerViewData("JACK"))
}