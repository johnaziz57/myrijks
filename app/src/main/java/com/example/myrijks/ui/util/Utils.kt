package com.example.myrijks.ui.util

import android.view.View
import com.google.android.material.snackbar.Snackbar

enum class SnackbarDuration(val duration: Int) {
    LONG(5_000),
    SHORT(1_000)
}

fun showActionableSnackbar(
    view: View,
    message: String,
    duration: SnackbarDuration,
    actionText: String,
    onClick: () -> Unit
) {
    Snackbar.make(view, message, duration.duration)
        .setAction(actionText) { onClick() }
        .show()
}