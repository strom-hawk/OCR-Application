package com.example.ocrapplication.ocr

import android.graphics.Bitmap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domainlayer.usecases.TextRecognizerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OCRViewModel @Inject constructor(
    private val useCase: TextRecognizerUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _extractedTextFromImage = MutableSharedFlow<String>()
    val extractedTextFromImage: SharedFlow<String> = _extractedTextFromImage

    fun getTextFromImage(bitmap: Bitmap) {
        viewModelScope.launch {
            _extractedTextFromImage.emitAll(useCase.getTextFromImage(bitmap))
        }
    }
}