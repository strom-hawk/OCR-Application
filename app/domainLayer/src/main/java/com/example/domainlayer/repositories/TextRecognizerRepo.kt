package com.example.domainlayer.repositories

import android.graphics.Bitmap
import kotlinx.coroutines.flow.Flow


interface TextRecognizerRepo {
    fun getTextFromImage(bitmap: Bitmap): Flow<String>
}