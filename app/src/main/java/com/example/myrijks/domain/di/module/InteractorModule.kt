package com.example.myrijks.domain.di.module

import com.example.myrijks.domain.CollectionInteractor
import com.example.myrijks.domain.CollectionInteractorImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface InteractorModule {
    @Binds
    fun bindCollectionInteractor(
        collectionInteractorImpl: CollectionInteractorImpl
    ): CollectionInteractor
}