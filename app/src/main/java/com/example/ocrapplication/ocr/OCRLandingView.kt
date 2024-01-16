package com.example.ocrapplication.ocr

import android.graphics.Bitmap
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.datalayer.data.ImageUIModel
import com.example.ocrapplication.utils.ColorSystem
import com.example.ocrapplication.utils.DescriptionField
import com.example.ocrapplication.utils.ImageStripComponent

@Composable
fun OCRLandingView(
    imageList: List<ImageUIModel>,
    currentImageIndex: MutableState<Int>,
    currentImageTags: MutableState<List<String>>,
    onImageChange: (Bitmap) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = ColorSystem.white),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        ImageViewComponent(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9F)
                .padding(horizontal = 16.dp),
            imagePath = imageList[currentImageIndex.value].path
        )
        Spacer(modifier = Modifier.height(16.dp))
        ImageStripComponent(imageList = imageList) { index, bitmap ->
            currentImageIndex.value = index
            currentImageTags.value = imageList[currentImageIndex.value].tags
            onImageChange(bitmap)
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}