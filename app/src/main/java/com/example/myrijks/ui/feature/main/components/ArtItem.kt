package com.example.myrijks.ui.feature.main.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Visibility
import coil.compose.AsyncImage
import com.example.myrijks.ui.feature.main.model.ArtViewData

@Composable
fun ArtView(artViewData: ArtViewData) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable { }
    ) {
        ConstraintLayout {
            val (image, title, row) = createRefs()
            val imageVisibility =
                if (artViewData.imageUrl == null) Visibility.Gone else Visibility.Visible
            AsyncImage(model = artViewData.imageUrl, contentDescription = null, modifier = Modifier
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    visibility = imageVisibility
                }
                .aspectRatio(1.7f))
            Text(text = artViewData.title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .constrainAs(title) {
                        top.linkTo(image.bottom)
                        start.linkTo(parent.start)
                    }
                    .wrapContentWidth()
                    .padding(horizontal = 4.dp, vertical = 8.dp))
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .constrainAs(row) {
                    top.linkTo(title.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 8.dp)) {
                Text(
                    text = artViewData.principalOrFirstMaker,
                    style = MaterialTheme.typography.bodyMedium,
                )
                Text(
                    text = artViewData.objectNumber,
                    style = MaterialTheme.typography.bodySmall,
                )
            }
        }
    }
}

@Preview
@Composable
fun ArtItemPreview() {
    ArtView(
        artViewData = ArtViewData(
            id = "123",
            imageUrl = "https://www.google.com/images/branding/googlelogo/2x/googlelogo_light_color_272x92dp.png",
            objectNumber = "objectNumber",
            principalOrFirstMaker = "Jack",
            title = "The scream"
        )
    )
}