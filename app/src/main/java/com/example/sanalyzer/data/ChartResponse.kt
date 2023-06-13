package com.example.sanalyzer.data

data class ChartResponse(
    val chart: Charts
)

data class Charts(
    val result: List<Result>
)

data class Result(
    val timestamp: List<Long>,
    val indicators: Indicators
)

data class Indicators(
    val quote: List<Quote>
)

data class Quote(
    val close: List<Float>
)