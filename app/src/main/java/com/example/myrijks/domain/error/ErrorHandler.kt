package com.example.myrijks.domain.error

import com.example.myrijks.domain.error.model.ErrorEntity

interface ErrorHandler {
    fun mapToError(throwable: Throwable): ErrorEntity
}