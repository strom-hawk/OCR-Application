package com.example.ocrapplication

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.ocrapplication.ocr.OCRViewHolder
import com.example.ocrapplication.ui.theme.OCRApplicationTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModels()
    private val STORAGE_REQUEST_CODE = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeImageList()
    }

    private fun observeImageList() {
        viewModel.imageListLiveData.observe(this) {
            it?.let { _imageList ->
                setContent {
                    OCRApplicationTheme {
                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colorScheme.background
                        ) {
                            OCRViewHolder(
                                imageList = _imageList
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onResume() {
        checkPermission()
        super.onResume()
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_MEDIA_IMAGES),
                    STORAGE_REQUEST_CODE
                )
            } else {
                viewModel.getAllImagesPath(this)
            }
        } else {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    STORAGE_REQUEST_CODE
                )
            } else {
                viewModel.getAllImagesPath(this)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == STORAGE_REQUEST_CODE) {
            var isPermissionGranted = true
            grantResults.forEach { _permissionStatus ->
                if (_permissionStatus != PackageManager.PERMISSION_GRANTED)
                    isPermissionGranted = false
            }
            if (grantResults.isNotEmpty() && isPermissionGranted) {
                viewModel.getAllImagesPath(this)
            } else {
                println("_____error while getting permission")
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}