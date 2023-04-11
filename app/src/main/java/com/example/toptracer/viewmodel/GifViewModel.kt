package com.example.toptracer.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toptracer.helpers.RetrofitInstance
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GifViewModel : ViewModel() {
    private val giphyApiService = RetrofitInstance.giphyApiService
    private val apiKey = "vYbi41ARNKCZHJvrZ7IlDdqfCGSb8ZZy"

    private val _url = mutableStateOf("")
    val url: State<String> get() = _url

    private val _title = mutableStateOf("")
    val title: State<String> get() = _title

    private val _prettyTitle = mutableStateOf("")
    val prettyTitle: State<String> get() = _prettyTitle

    private val _isLoading = mutableStateOf(true)
    val isLoading: State<Boolean> get() = _isLoading

    private fun formatTitle(title: String): Pair<String, String?> {
        val regex = Regex("^(.+) by (.+)$")
        val matchResult = regex.find(title)

        return if (matchResult != null) {
            val (formattedTitle, author) = matchResult.destructured
            Pair(formattedTitle, author)
        } else {
            Pair(title, null)
        }
    }

    fun getRandomGif() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val response = giphyApiService.getRandomGif(apiKey, "", "g")
                if (response.isSuccessful) {
                    val gifObject = response.body()?.data
                    _url.value = gifObject?.images?.fixedHeight?.url ?: ""
                    _title.value = gifObject?.title ?: ""
                    val (formattedTitle, author) = formatTitle(_title.value)
                    _prettyTitle.value = if (author != null) {
                        "\"$formattedTitle\" by $author"
                    } else {
                        "\"$formattedTitle\""
                    }
                } else {
                    /* TODO */
                }
            } catch (exception: Exception) {
                /* TODO */
            }
            _isLoading.value = false
        }
    }
}