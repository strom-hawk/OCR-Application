package com.example.datalayer.repositories

import android.content.Context
import android.graphics.Bitmap
import com.example.domainlayer.repositories.TextRecognizerRepo
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognizer
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch

class TextRecognizerRepoImpl(
    private val context: Context,
    private val recognizer: TextRecognizer,
) : TextRecognizerRepo {

    override fun getTextFromImage(bitmap: Bitmap): Flow<String> {
        return callbackFlow {
            val inputImage = InputImage.fromBitmap(bitmap, 0)

            recognizer.process(inputImage)
                .addOnSuccessListener {
                    launch {
                        send(it.text)
                    }
                }
                .addOnFailureListener {
                    it.printStackTrace()
                }
            awaitClose { }
        }
    }
}