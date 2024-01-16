package com.example.ocrapplication.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.datalayer.data.ImageUIModel
import java.io.File

@Composable
fun MenuFieldView(
    imageVector: ImageVector,
    title: String,
    onItemClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onItemClick() },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier
                .size(18.dp),
            imageVector = imageVector,
            contentDescription = "Share"
        )
        Text(
            text = title,
            fontSize = 14.sp
        )
    }
}

@Composable
fun BottomNav(
    currentTabIndex: Int,
    onBottomNavClick: (Int) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = ColorSystem.white),
        verticalArrangement = Arrangement.Center
    ) {
        Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp, color = ColorSystem.grey_300)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MenuFieldView(
                imageVector = if (currentTabIndex == 0) Icons.Filled.Share else Icons.Outlined.Share,
                title = "Share"
            ) {
                onBottomNavClick(0)
            }
            Spacer(modifier = Modifier.height(8.dp))
            MenuFieldView(
                imageVector = if (currentTabIndex == 1) Icons.Filled.Info else Icons.Outlined.Info,
                title = "Info"
            ) {
                onBottomNavClick(1)
            }
        }
    }
}

@Composable
fun ImageStripComponent(
    imageList: List<ImageUIModel>,
    onImageClick: (Int, Bitmap) -> Unit
) {
    LazyRow {
        items(imageList.size) { index ->
            val imageFile = File(imageList[index].path)
            var bitmap: Bitmap? = null
            if (imageFile.exists()) {
                bitmap = BitmapFactory.decodeFile(imageFile.absolutePath)
            }

            bitmap?.let { _bitmap ->
                AsyncImage(
                    modifier = Modifier
                        .width(30.dp)
                        .height(50.dp)
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
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = "Description",
            fontSize = 16.sp,
            fontWeight = FontWeight.W800,
            color = ColorSystem.grey_600
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = extractedText.ifEmpty { "No description available" },
            fontSize = 14.sp,
            fontWeight = FontWeight.W300,
            color = ColorSystem.grey_900
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun PrimaryButton(
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .background(
                color = ColorSystem.black,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            color = Color.White
        )
    }
}