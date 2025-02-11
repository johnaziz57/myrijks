package com.example.myrijks.ui.feature.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myrijks.ui.theme.MyRijksTypography

@Composable
fun SectionView(
    header: String,
    description: String
) {
    Column(Modifier.fillMaxWidth()) {
        Text(
            text = header,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp),
            style = MyRijksTypography.titleLarge
        )
        Text(
            text = description,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 8.dp),
            style = MyRijksTypography.bodyMedium
        )
    }
}

@Preview(widthDp = 100)
@Preview(widthDp = 500)
@Composable
fun SectionViewPreview() {
    SectionView("Header", "Description")
}
