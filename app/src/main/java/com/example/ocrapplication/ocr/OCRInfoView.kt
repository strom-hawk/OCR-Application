package com.example.ocrapplication.ocr

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.ocrapplication.utils.DescriptionField

/**
 * Created by Saurav Suman on 16/01/24.
 */

@Composable
fun OCRInfoView(
    imagePath: String,
    currentImageText: String
) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        ImageViewComponent(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7F)
                .padding(horizontal = 16.dp),
            imagePath = imagePath
        )
        Spacer(modifier = Modifier.height(16.dp))
        DescriptionField(extractedText = currentImageText)
    }
}