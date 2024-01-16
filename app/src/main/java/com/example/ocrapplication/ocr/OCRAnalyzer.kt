package com.example.ocrapplication.ocr

import android.content.Context
import android.graphics.BitmapFactory
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

fun recognizeText2(context: Context, id: Int): String {
/*    var text = ""
    val textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    val bitmap = BitmapFactory.decodeResource(context.resources, id)
    bitmap?.let { _bitmap ->
        val inputImage = InputImage.fromBitmap(_bitmap, 0)

        textRecognizer.process(inputImage)
            .addOnSuccessListener { _text ->
                text = _text.text
                println("_________success: $text")
            }
            .addOnFailureListener { _exception ->
                text = "Cannot decode image"
                println("_________failure: $text")
                _exception.printStackTrace()
            }
    }*/
    return "";
}