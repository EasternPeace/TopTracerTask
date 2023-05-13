package com.example.toptracer

import com.example.toptracer.data.GifRepository
import com.example.toptracer.data.GiphyApiService
import com.example.toptracer.data.RetrofitInstance
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideGiphyApiService(): GiphyApiService = RetrofitInstance.giphyApiService

    @Provides
    @Singleton
    fun provideGifRepository(giphyApiService: GiphyApiService): GifRepository = GifRepository(giphyApiService)
}