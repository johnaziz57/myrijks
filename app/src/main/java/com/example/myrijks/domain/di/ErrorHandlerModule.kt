package com.example.myrijks.domain.di

import com.example.myrijks.domain.error.ErrorHandler
import com.example.myrijks.domain.error.ErrorHandlerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface ErrorHandlerModule {

    @Binds
    fun bindErrorHandler(
        errorHandlerImpl: ErrorHandlerImpl
    ): ErrorHandler
}