package com.example.myrijks.data.di

import com.example.myrijks.data.api.AuthenticationInterceptor
import com.example.myrijks.data.api.RijksService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun providesPlacesService(): RijksService {

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(AuthenticationInterceptor())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.rijksmuseum.nl/")
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()

        return retrofit.create(RijksService::class.java)
    }
}