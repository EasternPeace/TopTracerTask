package com.example.toptracer.helpers

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

data class GiphyRandomResponse(
    val data: GiphyData
)

data class GiphyData(
    val id: String,
    val url: String,
    val title: String,
    val username: String,
    val images: GiphyImages
)

data class GiphyImages(
    val fixed_height: GiphyImageDetails
)

data class GiphyImageDetails(
    val url: String
)

data class GifInfo(
    val imageDetails: GiphyImageDetails,
    val title: String,
    val username: String
)

interface GiphyApi {
    @GET("v1/gifs/random")
    suspend fun getRandomGif(
        @Query("api_key") apiKey: String,
        @Query("tag") tag: String,
        @Query("rating") rating: String
    ): GiphyRandomResponse
}

fun createGiphyApiService(): GiphyApi {
    return Retrofit.Builder()
        .baseUrl("https://api.giphy.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GiphyApi::class.java)
}


suspend fun fetchRandomGif(apiKey: String, tag: String = "", rating: String = "g"): GifInfo? {
    val giphyApi = createGiphyApiService()
    return try {
        val response = withContext(Dispatchers.IO) {
            giphyApi.getRandomGif(apiKey, tag, rating)
        }
        GifInfo(
            imageDetails = response.data.images.fixed_height,
            title = response.data.title,
            username = response.data.username
        )
    } catch (e: Exception) {
        null
    }
}