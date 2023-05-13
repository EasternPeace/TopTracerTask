package com.example.toptracer.data

import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

data class GifResponse(
    val data: GifObject
)

data class GifObject(
    val title: String,
    val username: String,
    val images: Images
)

data class Images(
    @SerializedName("fixed_height")
    val fixedHeight: FixedHeight
)

data class FixedHeight(
    val url: String
)

interface GiphyApiService {
    @GET("v1/gifs/random")
    suspend fun getRandomGif(
        @Query("api_key") apiKey: String,
        @Query("tag") tag: String?,
        @Query("rating") rating: String?
    ): Response<GifResponse>
}

object RetrofitInstance {
    private const val BASE_URL = "https://api.giphy.com/"

    private val client = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    val giphyApiService: GiphyApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GiphyApiService::class.java)
}