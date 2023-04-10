package com.example.toptracer.viewmodel

import android.os.Bundle
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toptracer.helpers.RetrofitInstance
import kotlinx.coroutines.launch

class GifViewModel : ViewModel() {
    private val giphyApiService = RetrofitInstance.giphyApiService
    private val apiKey = "vYbi41ARNKCZHJvrZ7IlDdqfCGSb8ZZy"

    private val _url = mutableStateOf("")
    val url: State<String> get() = _url

    private val _username = mutableStateOf("")
    val username: State<String> get() = _username

    private val _title = mutableStateOf("")
    val title: State<String> get() = _title

    fun getRandomGif() {
        viewModelScope.launch {
            try {
                val response = giphyApiService.getRandomGif(apiKey, "", "g")
                if (response.isSuccessful) {
                    val gifObject = response.body()?.data
                    _url.value = gifObject?.images?.fixedHeight?.url ?: ""
                    _username.value = gifObject?.username ?: ""
                    _title.value = gifObject?.title ?: ""
                } else {
                    /* TODO */
                }
            } catch (exception: Exception) {
                /* TODO */
            }
        }
    }
}