package com.example.ocrapplication.ocr

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domainlayer.usecases.TextRecognizerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
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

//    private var _extractedTextFromImage = MutableStateFlow("")
//    val extractedTextFromImage = _extractedTextFromImage.asStateFlow()

    fun getTextFromImage(id: Int) {
        viewModelScope.launch {
            _extractedTextFromImage.emitAll(useCase.getTextFromImage(id))
        }
    }
}