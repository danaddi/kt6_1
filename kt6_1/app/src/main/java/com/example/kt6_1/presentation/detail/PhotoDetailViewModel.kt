package com.example.kt6_1.presentation.detail

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.URL

sealed class DownloadState {
    object Idle : DownloadState()
    object Loading : DownloadState()
    object Success : DownloadState()
    data class Error(val message: String) : DownloadState()
}

class PhotoDetailViewModel : ViewModel() {
    private val _downloadState = MutableStateFlow<DownloadState>(DownloadState.Idle)
    val downloadState: StateFlow<DownloadState> = _downloadState

    fun downloadPhoto(context: Context, imageUrl: String, fileName: String) {
        viewModelScope.launch {
            _downloadState.value = DownloadState.Loading
            try {
                withContext(Dispatchers.IO) {
                    val connection = URL(imageUrl).openConnection()
                    connection.connect()
                    val inputStream = connection.getInputStream()

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        // Используем MediaStore для Android 10+
                        val contentValues = ContentValues().apply {
                            put(MediaStore.Downloads.DISPLAY_NAME, fileName)
                            put(MediaStore.Downloads.MIME_TYPE, "image/jpeg")
                            put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
                        }

                        val resolver = context.contentResolver
                        val uri = resolver.insert(
                            MediaStore.Downloads.EXTERNAL_CONTENT_URI,
                            contentValues
                        )

                        uri?.let {
                            resolver.openOutputStream(it)?.use { outputStream ->
                                inputStream.copyTo(outputStream)
                            }
                        }
                    } else {
                        // Для более старых версий Android
                        val downloadsDir = Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_DOWNLOADS
                        )
                        val file = File(downloadsDir, fileName)
                        FileOutputStream(file).use { outputStream ->
                            inputStream.copyTo(outputStream)
                        }
                    }
                    inputStream.close()
                }
                _downloadState.value = DownloadState.Success
            } catch (e: Exception) {
                _downloadState.value = DownloadState.Error(
                    e.message ?: "Ошибка при скачивании"
                )
            }
        }
    }
}