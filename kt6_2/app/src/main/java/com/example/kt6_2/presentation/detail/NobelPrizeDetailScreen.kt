package com.example.kt6_2.presentation.detail


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.kt6_2.domain.model.NobelLaureate
import com.example.kt6_2.presentation.list.getCategoryDisplayName

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NobelPrizeDetailScreen(
    laureateId: String,
    viewModel: NobelPrizeDetailViewModel,
    onBackClick: () -> Unit
) {
    LaunchedEffect(laureateId) {
        viewModel.loadLaureateDetail(laureateId)
    }

    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Детали лауреата") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                    }
                }
            )
        }
    ) { paddingValues ->
        when (val currentState = state) {
            is LaureateDetailState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            is LaureateDetailState.Success -> {
                LaureateDetailContent(
                    laureate = currentState.laureate,
                    modifier = Modifier.padding(paddingValues)
                )
            }

            is LaureateDetailState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = currentState.message,
                            color = MaterialTheme.colorScheme.error
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = { viewModel.loadLaureateDetail(laureateId) }) {
                            Text("Повторить")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LaureateDetailContent(
    laureate: NobelLaureate,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Имя лауреата
        Text(
            text = laureate.fullName,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Карточка с основной информацией
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Год и категория
                InfoRow(label = "Год премии", value = laureate.awardYear ?: "Н/Д")
                InfoRow(label = "Категория", value = getCategoryDisplayName(laureate.category ?: ""))

                // Дата рождения
                if (laureate.birthDate != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    InfoRow(label = "Дата рождения", value = laureate.birthDate)
                }

                // Место рождения
                if (laureate.birthPlace.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(8.dp))
                    InfoRow(label = "Место рождения", value = laureate.birthPlace)
                }

                // Дата смерти
                if (laureate.deathDate != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    InfoRow(label = "Дата смерти", value = laureate.deathDate)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Мотивация (полное описание)
        if (laureate.motivation.isNotEmpty()) {
            Text(
                text = "Мотивация Нобелевского комитета",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(8.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ) {
                Text(
                    text = laureate.motivation,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        // Кнопка Wikipedia
        if (laureate.wikipediaUrl != null) {
            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    // Здесь можно открыть ссылку в браузере
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Language, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Открыть в Wikipedia")
            }
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium
        )
    }
}