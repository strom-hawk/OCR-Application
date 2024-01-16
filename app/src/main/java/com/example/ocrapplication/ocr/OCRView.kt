package com.example.ocrapplication.ocr

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ocrapplication.utils.ColorSystem
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
    val currentImageIndex = remember { mutableStateOf(value = 0) }
    val currentImageText = remember { mutableStateOf(value = "") }
    //onImageChange(imageList[currentImageIndex.value])

    LaunchedEffect(key1 = extractedText) {
        extractedText.collect {
            currentImageText.value = it
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = ColorSystem.white),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        ImageViewComponent(imagePath = imageList[currentImageIndex.value])
        ImageStripComponent(imageList = imageList) { index, bitmap ->
            currentImageIndex.value = index
            onImageChange(bitmap)
        }
        DescriptionField(extractedText = currentImageText.value)

    }
}


@Composable
fun ImageViewComponent(imagePath: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.6F)
            .padding(horizontal = 16.dp)
    ) {
        val imageFile = File(imagePath)
        var bitmap: Bitmap? = null
        if (imageFile.exists()) {
            bitmap = BitmapFactory.decodeFile(imageFile.absolutePath)
        }

        bitmap?.let { _bitmap ->
            AsyncImage(
                modifier = Modifier
                    .size(50.dp),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(imageFile.absolutePath)
                    .build(),
                contentDescription = "icon",
                contentScale = ContentScale.Inside,
            )
        }
    }
}

@Composable
fun ImageStripComponent(
    imageList: List<String>,
    onImageClick: (Int, Bitmap) -> Unit
) {
    LazyRow {
        items(imageList.size) { index ->
            val imageFile = File(imageList[index])
            var bitmap: Bitmap? = null
            if (imageFile.exists()) {
                bitmap = BitmapFactory.decodeFile(imageFile.absolutePath)
            }

            bitmap?.let { _bitmap ->
                AsyncImage(
                    modifier = Modifier
                        .size(50.dp)
                        .clickable { onImageClick(index, _bitmap) },
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(imageFile.absolutePath)
                        .build(),
                    contentDescription = "icon",
                    contentScale = ContentScale.Inside,
                )
            }
        }
    }
}

@Composable
fun DescriptionField(extractedText: String) {
    Text(
        text = extractedText
    )
}