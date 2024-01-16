package com.example.ocrapplication.ocr

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ocrapplication.utils.BottomNav
import kotlinx.coroutines.flow.SharedFlow
import java.io.File

@Composable
fun OCRViewHolder(
    imageList: List<String>
) {
    val viewModel = hiltViewModel<OCRViewModel>()
    val extractedText = viewModel.extractedTextFromImage

    OCRView(
        imageList = imageList,
        extractedText = extractedText
    ) { _bitmap ->
        viewModel.getTextFromImage(_bitmap)
    }
}

@Composable
fun OCRView(
    imageList: List<String>,
    extractedText: SharedFlow<String>,
    onImageChange: (Bitmap) -> Unit
) {
    val currentTabIndex = remember { mutableStateOf(value = 0) }
    val currentImageIndex = remember { mutableStateOf(value = 0) }
    val currentImageText = remember { mutableStateOf(value = "") }

    LaunchedEffect(key1 = extractedText) {
        extractedText.collect {
            currentImageText.value = it
        }
    }

    Column {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.92F)
        ) {
            if (currentTabIndex.value == 0) {
                OCRLandingView(
                    imageList = imageList,
                    currentImageIndex = currentImageIndex
                ) {
                    onImageChange(it)
                }
            } else {
                OCRInfoView(
                    imagePath = imageList[currentImageIndex.value],
                    currentImageText = currentImageText.value
                )
            }
        }

        BottomNav(currentTabIndex.value) {
            currentTabIndex.value = it
        }
    }
}


@Composable
fun ImageViewComponent(
    modifier: Modifier,
    imagePath: String
) {
    val imageFile = File(imagePath)
    var bitmap: Bitmap? = null
    if (imageFile.exists()) {
        bitmap = BitmapFactory.decodeFile(imageFile.absolutePath)
    }

    bitmap?.let { _bitmap ->
        AsyncImage(
            modifier = modifier,
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageFile.absolutePath)
                .build(),
            contentDescription = "icon",
            contentScale = ContentScale.Fit,
        )
    }
}


@Preview
@Composable
fun MenuFieldViewPreview() {

}