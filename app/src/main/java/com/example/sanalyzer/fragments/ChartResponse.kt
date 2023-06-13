package com.example.sanalyzer.fragments

data class ChartResponse(
    val entries: List<ChartEntry>
)

data class ChartEntry(
    val timestamp: Long,
    val close: Float
)