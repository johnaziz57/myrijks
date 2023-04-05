package com.example.myrijks.data.model

data class Image(
    val guid: String,
    val offsetPercentageX: Long,
    val offsetPercentageY: Long,
    val width: Long,
    val height: Long,
    val url: String,
)