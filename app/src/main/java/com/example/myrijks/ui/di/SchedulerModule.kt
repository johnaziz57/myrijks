package com.example.myrijks.ui.di

import com.example.myrijks.ui.viewmodel.SchedulerProvider
import com.example.myrijks.ui.viewmodel.SchedulerProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface SchedulerModule {
    @Binds
    fun bindSchedulerProvider(
        schedulerProviderImpl: SchedulerProviderImpl
    ): SchedulerProvider
}