package com.example.domainlayer.repositories

import kotlinx.coroutines.flow.Flow


interface TextRecognizerRepo {
    fun getTextFromImage(id: Int): Flow<String>
}