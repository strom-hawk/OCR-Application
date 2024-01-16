package com.example.domainlayer.usecases

import android.graphics.Bitmap
import com.example.domainlayer.repositories.TextRecognizerRepo
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow


class TextRecognizerUseCase(
    private val repo: TextRecognizerRepo
) {
    fun getTextFromImage(bitmap: Bitmap) = flow {
        emitAll(repo.getTextFromImage(bitmap))
    }
}