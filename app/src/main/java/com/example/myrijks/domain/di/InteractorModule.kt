package com.example.myrijks.domain.di

import com.example.myrijks.domain.interactor.CollectionInteractor
import com.example.myrijks.domain.interactor.CollectionInteractorImpl
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