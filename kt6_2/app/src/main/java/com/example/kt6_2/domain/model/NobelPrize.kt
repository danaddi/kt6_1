package com.example.kt6_2.domain.model

data class NobelPrize(
    val awardYear: String,
    val category: String,
    val categoryFullName: String,
    val dateAwarded: String?,
    val prizeAmount: Int?,
    val prizeAmountAdjusted: Int?,
    val laureates: List<NobelLaureate>
)