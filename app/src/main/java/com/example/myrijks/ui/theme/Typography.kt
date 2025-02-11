package com.example.myrijks.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.example.myrijks.R

val RobotoFontFamily = FontFamily(
    Font(resId = R.font.roboto_medium),
    Font(resId = R.font.roboto_regular)
)

val MyRijksTypography = Typography(
    titleLarge = TextStyle(
        fontSize = 22.sp,
        fontFamily = RobotoFontFamily
    ),
    titleMedium = TextStyle(
        fontSize = 18.sp,
        fontFamily = RobotoFontFamily
    ),
    bodyMedium = TextStyle(
        fontSize = 16.sp,
        fontFamily = RobotoFontFamily
    ),
    bodySmall = TextStyle(
        fontSize = 14.sp,
        fontFamily = RobotoFontFamily
    )
)