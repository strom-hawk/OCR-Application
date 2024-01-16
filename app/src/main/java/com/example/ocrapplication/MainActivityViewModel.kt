package com.example.ocrapplication

import android.content.Context
import android.provider.MediaStore
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datalayer.data.ImageUIModel
import kotlinx.coroutines.launch

class MainActivityViewModel(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val imageListLiveData = MutableLiveData<List<ImageUIModel>>()

    fun getAllImagesPath(context: Context) {
        viewModelScope.launch {
            val imageList = mutableListOf<String>()
            val uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
            val projection = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
            val cursor = context.contentResolver.query(uri, projection, null, null, null)
            cursor?.let {
                val columnIndexData = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
                while (cursor.moveToNext()) {
                    val imagePath = cursor.getString(columnIndexData)
                    imageList.add(imagePath)
                    println("-------imagepath: $imagePath")
                }
            }

            imageListLiveData.value = listOf()
            val imageUIModelList = mutableListOf<ImageUIModel>()
            imageList.forEach { imagePath ->
                imageUIModelList.add(ImageUIModel(imagePath))
            }

            imageListLiveData.value = imageUIModelList.reversed()
        }
    }
}