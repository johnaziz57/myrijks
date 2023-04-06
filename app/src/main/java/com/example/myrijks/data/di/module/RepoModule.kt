package com.example.myrijks.data.di.module

import com.example.myrijks.data.repo.CollectionRepository
import com.example.myrijks.data.repo.CollectionRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepoModule {
    @Binds
    fun bindCollectionRepository(
        collectionRepository: CollectionRepositoryImpl
    ): CollectionRepository
}