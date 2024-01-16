package com.example.ocrapplication.ocr

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.datalayer.data.ImageUIModel
import com.example.ocrapplication.utils.BottomNav
import com.example.ocrapplication.utils.ColorSystem
import com.example.ocrapplication.utils.PrimaryButton
import kotlinx.coroutines.flow.SharedFlow
import java.io.File

@Composable
fun OCRViewHolder(
    imageList: List<ImageUIModel>
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
    imageList: List<ImageUIModel>,
    extractedText: SharedFlow<String>,
    onImageChange: (Bitmap) -> Unit
) {
    val currentTabIndex = remember { mutableStateOf(value = 0) }
    val currentImageIndex = remember { mutableStateOf(value = 0) }
    val currentImageText = remember { mutableStateOf(value = "") }
    val currentImageTags = remember { mutableStateOf(value = listOf<String>()) }
    val showEditBottomSheet = remember { mutableStateOf(value = false) }

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
                    currentImageIndex = currentImageIndex,
                    currentImageTags = currentImageTags
                ) {
                    onImageChange(it)
                }
            } else {
                OCRInfoView(
                    imagePath = imageList[currentImageIndex.value].path,
                    tags = currentImageTags.value,
                    currentImageText = currentImageText.value
                ) {
                    showEditBottomSheet.value = true
                }
            }
        }

        BottomNav(currentTabIndex.value) {
            currentTabIndex.value = it
        }
    }

    CollectionBottomSheet(
        showEditBottomSheet.value,
        imageList[currentImageIndex.value].tags,
        {
            val tempTags = imageList[currentImageIndex.value].tags.toMutableList()
            tempTags.add(it)
            imageList[currentImageIndex.value].tags = tempTags
        }
    ){
        showEditBottomSheet.value = false
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CollectionBottomSheet(
    showEditBottomSheet: Boolean,
    tags: List<String>,
    addTagToList: (String) -> Unit,
    onDismiss: () -> Unit
) {
    val modalBottomSheetState = rememberModalBottomSheetState()

    if(showEditBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { onDismiss() },
            sheetState = modalBottomSheetState,
            dragHandle = { BottomSheetDefaults.DragHandle() },
        ) {
            Column (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ){
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Collections",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W800,
                    color = ColorSystem.grey_900
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Select an option",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.W400,
                    color = ColorSystem.grey_900
                )
                Spacer(modifier = Modifier.height(8.dp))
                FlowRow() {
                    Tags("Animal", tags, addTagToList)
                    Tags("Books", tags, addTagToList)
                    Tags("Travel", tags, addTagToList)
                    Tags("Science", tags, addTagToList)
                }
                Spacer(modifier = Modifier.height(100.dp))
                PrimaryButton(title = "Done") {
                    onDismiss()
                }
                Spacer(modifier = Modifier.height(54.dp))
            }
        }
    }
}

@Composable
fun Tags(
    title: String,
    tagsList: List<String>,
    addTagToList: (String) -> Unit
) {
    Row(
        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .background(color = ColorSystem.blue_100, shape = RoundedCornerShape(12.dp))
                .padding(horizontal = 10.dp, vertical = 4.dp)
                .clickable { addTagToList(title) },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = title)
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                modifier = Modifier.size(14.dp),
                imageVector = if(tagsList.contains(title)) Icons.Rounded.Check else Icons.Rounded.Add,
                contentDescription = "Add"
            )
        }
    }
}