package com.example.myrijks.domain.util

import com.example.myrijks.domain.model.error.ErrorEntity

sealed class Result<T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error<T>(val error: ErrorEntity) : Result<T>()
}