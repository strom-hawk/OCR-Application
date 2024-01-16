package com.example.ocrapplication.ocr

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ocrapplication.utils.ColorSystem
import com.example.ocrapplication.utils.DescriptionField

/**
 * Created by Saurav Suman on 16/01/24.
 */

@Composable
fun OCRInfoView(
    imagePath: String,
    tags: List<String>,
    currentImageText: String,
    onEditClick: () -> Unit,
) {
    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        ImageViewComponent(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8F)
                .padding(horizontal = 16.dp),
            imagePath = imagePath
        )
        Spacer(modifier = Modifier.height(16.dp))
        CollectionField(tags = tags, onEditClick = onEditClick)
        Spacer(modifier = Modifier.height(16.dp))
        DescriptionField(extractedText = currentImageText)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CollectionField(
    tags: List<String>,
    onEditClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Collections",
                fontSize = 16.sp,
                fontWeight = FontWeight.W800,
                color = ColorSystem.grey_600
            )

            Text(
                modifier = Modifier.clickable { onEditClick() },
                text = "Edit",
                fontSize = 14.sp,
                fontWeight = FontWeight.W300,
                color = ColorSystem.grey_900
            )
        }

        if(tags.isEmpty()) {
            Text(
                text = "No tags available",
                fontSize = 14.sp,
                fontWeight = FontWeight.W300,
                color = ColorSystem.grey_900
            )
        } else {
            FlowRow {
                tags.forEach{
                    Row(
                        modifier = Modifier
                            .background(color = ColorSystem.blue_100, shape = RoundedCornerShape(12.dp))
                            .padding(horizontal = 10.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(text = it)
                    }
                }
            }
        }
    }

}