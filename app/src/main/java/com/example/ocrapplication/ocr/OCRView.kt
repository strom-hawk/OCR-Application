package com.example.ocrapplication.ocr

import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ocrapplication.R
import com.example.ocrapplication.utils.ColorSystem
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect

@Composable
fun OCRViewHolder() {
    val viewModel = hiltViewModel<OCRViewModel>()
    val extractedText = viewModel.extractedTextFromImage

    OCRView(
        extractedText = extractedText
    ){ _currentImageId ->
        viewModel.getTextFromImage(_currentImageId)
    }
}

@Composable
fun OCRView(
    extractedText: SharedFlow<String>,
    onImageChange: (Int) -> Unit
) {
    val currentImageId = remember { mutableStateOf(value = R.drawable.image_1) }
    val currentImageText = remember { mutableStateOf(value = "") }
    onImageChange(currentImageId.value)

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
        ImageViewComponent(id = currentImageId.value)
        DescriptionField(extractedText = currentImageText.value)
    }
}


@Composable
fun ImageViewComponent(id: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.6F)
            .padding(horizontal = 16.dp)
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(),
            painter = painterResource(id = id),
            contentDescription = "Image Preview",
            contentScale = ContentScale.Fit
        )
    }
}

@Composable
fun DescriptionField(extractedText: String) {
    Text(
        text = extractedText
    )
}

@Preview
@Composable
fun OCRViewPreview() {
    OCRViewHolder()
}