package com.example.myrijks.domain.util

import com.example.myrijks.domain.error.ErrorHandler
import io.reactivex.rxjava3.core.Single

fun <T : Any> Single<T>.toResult(errorHandler: ErrorHandler): Single<Result<T>> = this
    .map<Result<T>> {
        Result.Success(it)
    }
    .onErrorReturn {
        Result.Error(errorHandler.mapToError(it))
    }