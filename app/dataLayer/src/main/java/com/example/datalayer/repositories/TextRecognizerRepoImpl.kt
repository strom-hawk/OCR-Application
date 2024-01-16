package com.example.datalayer.repositories

import com.example.domainlayer.repositories.TextRecognizerRepo
import com.google.mlkit.vision.text.TextRecognizer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import android.content.Context
import android.graphics.BitmapFactory
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.launch

class TextRecognizerRepoImpl(
    private val context: Context,
    private val recognizer: TextRecognizer,
): TextRecognizerRepo {

    override fun getTextFromImage(id: Int): Flow<String> {
        val bitmap = BitmapFactory.decodeResource(context.resources, id)

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
            awaitClose {  }
        }
    }
}