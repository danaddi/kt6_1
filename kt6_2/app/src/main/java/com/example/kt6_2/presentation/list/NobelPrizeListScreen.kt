package com.example.kt6_2.presentation.list


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.kt6_2.domain.model.NobelPrize

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NobelPrizeListScreen(
    viewModel: NobelPrizeListViewModel,
    onLaureateClick: (NobelPrize) -> Unit
) {
    val state by viewModel.state.collectAsState()
    val filterState by viewModel.filterState.collectAsState()

    var showFilterDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Нобелевские лауреаты") },
                actions = {
                    IconButton(onClick = { showFilterDialog = true }) {
                        Icon(Icons.Default.Search, contentDescription = "Фильтр")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Показываем активные фильтры
            if (filterState.year.isNotEmpty() || filterState.category.isNotEmpty()) {
                FilterChips(
                    filterState = filterState,
                    onClearFilter = {
                        viewModel.updateFilter("", "")
                    }
                )
            }

            // Контент в зависимости от состояния
            when (val currentState = state) {
                is NobelPrizeListState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is NobelPrizeListState.Success -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(currentState.prizes) { prize ->
                            NobelPrizeCard(
                                prize = prize,
                                onClick = { onLaureateClick(prize) }
                            )
                        }
                    }
                }

                is NobelPrizeListState.Error -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(
                                text = currentState.message,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.error
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = { viewModel.loadPrizes() }) {
                                Text("Повторить")
                            }
                        }
                    }
                }
            }
        }
    }

    // Диалог фильтрации
    if (showFilterDialog) {
        FilterDialog(
            currentYear = filterState.year,
            currentCategory = filterState.category,
            onApplyFilter = { year, category ->
                viewModel.updateFilter(year, category)
                showFilterDialog = false
            },
            onDismiss = { showFilterDialog = false }
        )
    }
}

@Composable
fun FilterChips(
    filterState: FilterState,
    onClearFilter: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("Фильтры:", style = MaterialTheme.typography.bodyMedium)

        if (filterState.year.isNotEmpty()) {
            AssistChip(
                onClick = onClearFilter,
                label = { Text("Год: ${filterState.year}") }
            )
        }

        if (filterState.category.isNotEmpty()) {
            AssistChip(
                onClick = onClearFilter,
                label = { Text(getCategoryDisplayName(filterState.category)) }
            )
        }

        TextButton(onClick = onClearFilter) {
            Text("Сбросить")
        }
    }
}

@Composable
fun NobelPrizeCard(
    prize: NobelPrize,
    onClick: () -> Unit
) {
    val laureate = prize.laureates.firstOrNull() ?: return

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = prize.awardYear,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                CategoryBadge(category = prize.category)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = laureate.fullName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium
            )

            if (laureate.motivation.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = laureate.shortMotivation,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun CategoryBadge(category: String) {
    val categoryColor = when (category.lowercase()) {
        "physics" -> MaterialTheme.colorScheme.primary
        "chemistry" -> MaterialTheme.colorScheme.secondary
        "literature" -> MaterialTheme.colorScheme.tertiary
        "peace" -> MaterialTheme.colorScheme.error
        "medicine" -> MaterialTheme.colorScheme.primary
        "economics" -> MaterialTheme.colorScheme.secondary
        else -> MaterialTheme.colorScheme.outline
    }

    Surface(
        color = categoryColor.copy(alpha = 0.1f),
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text = getCategoryDisplayName(category),
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            color = categoryColor
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterDialog(
    currentYear: String,
    currentCategory: String,
    onApplyFilter: (year: String, category: String) -> Unit,
    onDismiss: () -> Unit
) {
    var year by remember { mutableStateOf(currentYear) }
    var category by remember { mutableStateOf(currentCategory) }
    var categoryExpanded by remember { mutableStateOf(false) }

    val categories = listOf(
        "" to "Все категории",
        "phy" to "Физика",
        "che" to "Химия",
        "lit" to "Литература",
        "pea" to "Мир",
        "med" to "Медицина",
        "eco" to "Экономика"
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Фильтр лауреатов") },
        text = {
            Column {
                OutlinedTextField(
                    value = year,
                    onValueChange = {
                        if (it.all { char -> char.isDigit() } && it.length <= 4) {
                            year = it
                        }
                    },
                    label = { Text("Год (например, 2023)") },
                    placeholder = { Text("1901-2024") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                ExposedDropdownMenuBox(
                    expanded = categoryExpanded,
                    onExpandedChange = { categoryExpanded = it }
                ) {
                    OutlinedTextField(
                        value = categories.find { it.first == category }?.second ?: "Все категории",
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Категория") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryExpanded)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = categoryExpanded,
                        onDismissRequest = { categoryExpanded = false }
                    ) {
                        categories.forEach { (value, label) ->
                            DropdownMenuItem(
                                text = { Text(label) },
                                onClick = {
                                    category = value
                                    categoryExpanded = false
                                }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(onClick = { onApplyFilter(year, category) }) {
                Text("Применить")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Отмена")
            }
        }
    )
}

fun getCategoryDisplayName(category: String): String {
    return when (category.lowercase()) {
        "phy", "physics" -> "Физика"
        "che", "chemistry" -> "Химия"
        "lit", "literature" -> "Литература"
        "pea", "peace" -> "Мир"
        "med", "medicine" -> "Медицина"
        "eco", "economics" -> "Экономика"
        else -> category
    }
}