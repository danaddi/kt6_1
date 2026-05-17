package com.example.kt6_1.presentation.detail

import android.content.Intent
import android.provider.MediaStore
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.kt6_1.domain.model.Photo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoDetailScreen(
    photo: Photo,
    viewModel: PhotoDetailViewModel,
    onBackClick: () -> Unit
) {
    val downloadState by viewModel.downloadState.collectAsState()
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Детали фото") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
        ) {
            // Большое изображение
            AsyncImage(
                model = photo.downloadUrl,
                contentDescription = "Фото от ${photo.author}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp),
                contentScale = ContentScale.Fit
            )

            // Информация о фото
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Автор: ${photo.author}",
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Размеры: ${photo.width} × ${photo.height}",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Ссылка: ${photo.url}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Кнопка скачивания
                Button(
                    onClick = {
                        viewModel.downloadPhoto(
                            context = context,
                            imageUrl = photo.downloadUrl,
                            fileName = "photo_${photo.id}.jpg"
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = downloadState !is DownloadState.Loading
                ) {
                    if (downloadState is DownloadState.Loading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Icon(Icons.Default.Download, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Скачать фото")
                    }
                }

                // Сообщения о состоянии скачивания
                when (val state = downloadState) {
                    is DownloadState.Success -> {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Фото успешно сохранено в папку Downloads",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    is DownloadState.Error -> {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = state.message,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    else -> {}
                }
            }
        }
    }
}