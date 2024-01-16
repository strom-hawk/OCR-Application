package com.example.domainlayer.usecases

import com.example.domainlayer.repositories.TextRecognizerRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow


class TextRecognizerUseCase(
    private val repo: TextRecognizerRepo
) {
    fun getTextFromImage(id: Int) = flow {
        emitAll(repo.getTextFromImage(id))
    }
}