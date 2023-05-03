package com.example.toptracer.helpers

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

data class TopTracerPasswordTransformation(
    private val mask: Char = '•',
    private val unmaskDelay: Int = 1500
) : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {
        val transformedText = buildString {
            if (text.text.isNotEmpty()) {
                append(mask.toString().repeat(text.text.length - 1))
                append(text.text.last())
            }
        }

        return TransformedText(
            AnnotatedString(transformedText),
            OffsetMapping.Identity
        )
    }
}