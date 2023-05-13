package com.example.toptracer.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.toptracer.data.GifRepository
import com.example.toptracer.ui.state.GifUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(private val gifRepository: GifRepository) : ViewModel() {
    private val _gifUiState = MutableStateFlow(GifUiState())
    val gifUiState: StateFlow<GifUiState> = _gifUiState.asStateFlow()
    private val _prettyTitle = mutableStateOf("")
    val prettyTitle: State<String> get() = _prettyTitle

    private fun formatTitle(title: String): Pair<String?, String?> {
        val regex = Regex("^(.+) by (.+)$")
        val matchResult = regex.find(title)

        return if (matchResult != null) {
            val (formattedTitle, author) = matchResult.destructured
            Pair(formattedTitle, author)
        } else {
            Pair(null, null)
        }
    }

    private suspend fun fetchRandomGif() {
        try {
            val gifObject = gifRepository.getRandomGif("", "")?.data
            if (gifObject != null) {
                updateGifUiState(
                    url = gifObject.images.fixedHeight.url,
                    title = gifObject.title
                )
                updatePrettyTitle(_gifUiState.value.title)

            } else {
                /* TODO */
            }
        } catch (exception: Exception) {
            /* TODO */
        }
    }

    private fun updateGifUiState(url: String, title: String) {
        _gifUiState.update { currentState ->
            currentState.copy(
                url = url,
                title = title
            )
        }
    }

    private fun updatePrettyTitle(title: String) {
        val (formattedTitle, author) = formatTitle(title)
        _prettyTitle.value = if (author != null) {
            "\"$formattedTitle\" by $author"
        } else if (formattedTitle != null) {
            "\"$formattedTitle\""
        } else {
            "Unnamed GIF by Unnamed!"
        }
    }

    fun getRandomGif() {
        _gifUiState.update { currentState ->
            currentState.copy(
                isLoading = true
            )
        }
        viewModelScope.launch {
            fetchRandomGif()
            _gifUiState.update { currentState ->
                currentState.copy(
                    isLoading = false
                )
            }
        }
    }

    fun resetGifState() {
        _gifUiState.update { currentState ->
            currentState.copy(
                url = "",
                title = "",
                isLoading = true
            )
        }
    }
}