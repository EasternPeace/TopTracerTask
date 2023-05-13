package com.example.toptracer.data

class GifRepository(private val giphyApiService: GiphyApiService) {
    private val apiKey = "k7TXlf0ZIUczzeje9BQ28Bemh5QWZLnk"

    suspend fun getRandomGif(tag: String? = "", rating: String? = ""): GifResponse? {
        return try {
            val response = giphyApiService.getRandomGif(apiKey, tag, rating)
            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (exception: Exception) {
            null
        }
    }
}