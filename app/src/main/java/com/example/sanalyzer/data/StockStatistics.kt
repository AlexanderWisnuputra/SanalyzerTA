package com.example.sanalyzer.data

data class StockStatistics(
    val summaryDetail: SummaryDetail,
    val financialData: FinancialData,
    val defaultKeyStatistics: DefaltkeyStatistics,
    val price: Price,
    val summaryProfile: SummaryProfile
)

data class SummaryDetail(
    val marketCap: RegularValue,
    val dividendYield: RegularValue,
    val fiftyTwoWeekLow: RegularValue,
    val fiftyTwoWeekHigh: RegularValue,
    val volume: RegularValue
)

data class FinancialData(
    val grossProfits: RegularValue,
    val returnOnAssets: RegularValue,
    val returnOnEquity: RegularValue,
    val profitMargins: RegularValue
)

data class DefaltkeyStatistics(
    val priceToBook: RegularValue,
    val bookValue: RegularValue,
    val trailingEps: RegularValue
    )

data class Price(
    val regularMarketChange: RegularValue,
    val regularMarketChangePercent: RegularValue,
    val averageDailyVolume3Month: RegularValue,
    val regularMarketPrice: RegularValue
)

data class SummaryProfile(
    val sector: String
)
data class RegularValue(
    val raw: Double,
    val fmt: String
    )
