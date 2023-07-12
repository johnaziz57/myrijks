package com.example.myrijks.data.di

import com.example.myrijks.data.repo.CollectionRepositoryImpl
import com.example.myrijks.domain.repo.CollectionRepository
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