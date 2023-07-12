package com.example.myrijks.domain.error

import com.example.myrijks.domain.model.error.ErrorEntity

interface ErrorHandler {
    fun mapToError(throwable: Throwable): ErrorEntity
}