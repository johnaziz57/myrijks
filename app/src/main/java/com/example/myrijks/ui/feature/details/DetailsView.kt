package com.example.myrijks.ui.feature.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myrijks.domain.model.details.ArtDetailsEntity
import com.example.myrijks.ui.theme.MyRijksTypography

@Composable
fun DetailsView(artDetailsEntity: ArtDetailsEntity) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .verticalScroll(rememberScrollState())
    ) {
        artDetailsEntity.imageUrl?.let { imageUrl ->
            if (imageUrl.isNotBlank()) {
                AsyncImage(
                    model = imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                )
            }
        }
        Text(
            text = artDetailsEntity.title,
            modifier = Modifier.align(Alignment.Start),
            style = MyRijksTypography.titleLarge
        )

        Text(
            text = artDetailsEntity.subTitle,
            modifier = Modifier.align(Alignment.Start),
            style = MyRijksTypography.bodyMedium
        )

        Text(
            text = artDetailsEntity.scLabelLine,
            modifier = Modifier.align(Alignment.Start),
            style = MyRijksTypography.bodyMedium
        )

        if (artDetailsEntity.description.isNotBlank()) {
            SectionView("Description", artDetailsEntity.description)
            SectionDividerView()
        }

        if (artDetailsEntity.principalMakers.isNotEmpty()) {
            SectionView(
                "Maker(s)",
                artDetailsEntity.principalMakers.joinToString("\n")
            )
            SectionDividerView()
        }

        if (artDetailsEntity.physicalMedium.isNotBlank()) {
            SectionView(
                "Physical Medium",
                artDetailsEntity.physicalMedium
            )
            SectionDividerView()
        }

        if (artDetailsEntity.dimensionDescription.isNotBlank()) {
            SectionView(
                "Dimensions",
                artDetailsEntity.dimensionDescription
            )
            SectionDividerView()
        }

        if (artDetailsEntity.dating.isNotBlank()) {
            SectionView("Dating", artDetailsEntity.dating)
            SectionDividerView()
        }

        if (artDetailsEntity.productionPlacesDescription.isNotBlank()) {
            SectionView(
                "Production Place(s)",
                artDetailsEntity.productionPlacesDescription
            )
            SectionDividerView()
        }
    }
}