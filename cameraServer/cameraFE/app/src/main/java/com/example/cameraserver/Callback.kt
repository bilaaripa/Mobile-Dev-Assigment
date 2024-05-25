package com.example.cameraserver

import android.graphics.Bitmap

interface MainActivityCallback {
    fun updateImageView(imageBitmap : Bitmap)
    fun showUploadButton()
}