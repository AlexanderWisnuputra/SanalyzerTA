package com.example.sanalyzer.data

data class CandleStick (
    val chart: Candle
)

data class Candle(
    val result: List<Res>
)

data class Res(
    val timestamp: List<Long>,
    val indicators: Ind
)

data class Ind(
    val quote: List<Quo>
)

data class Quo(
    val open: List<Float>,
    val high: List<Float>,
    val low: List<Float>,
    val close: List<Float>
)
