package com.example.sanalyzer.api

import com.example.sanalyzer.data.CandleStick
import com.example.sanalyzer.data.ChartResponse
import com.example.sanalyzer.data.StockStatistics
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class YahooFinanceApiClient {
    val apiService: ApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://apidojo-yahoo-finance-v1.p.rapidapi.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(ApiService::class.java)
    }

    fun getChart(
        symbol: String,
        interval: String,
        range: String,
        callback: (List<Long>, List<Float>) -> Unit
    ) {
        val call = apiService.getChart(symbol, interval, range)
        call.enqueue(object : retrofit2.Callback<ChartResponse> {
            override fun onResponse(
                call: Call<ChartResponse>,
                response: retrofit2.Response<ChartResponse>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()?.chart?.result?.get(0)
                    result?.let {
                        callback(it.timestamp, it.indicators.quote[0].close)
                    }
                } else {
                    // Handle API error
                }
            }

            override fun onFailure(call: Call<ChartResponse>, t: Throwable) {
                // Handle network or request error
            }
        })
    }

    fun candlestick(
        symbol: String,
        interval: String,
        range: String,
        callback: (List<Long>, List<Float>, List<Float>, List<Float>, List<Float>) -> Unit
    ) {
        val call = apiService.getcandle(symbol, interval, range)
        call.enqueue(object : retrofit2.Callback<CandleStick> {
            override fun onResponse(
                call: Call<CandleStick>,
                response: retrofit2.Response<CandleStick>
            ) {
                if (response.isSuccessful) {
                    val result = response.body()?.chart?.result?.get(0)
                    result?.let {
                        callback(it.timestamp, it.indicators.quote[0].open, it.indicators.quote[0].high,it.indicators.quote[0].low,it.indicators.quote[0].close)}
                } else {
                    // Handle API error
                }
            }

            override fun onFailure(call: Call<CandleStick>, t: Throwable) {
                // Handle network or request error
            }
        })
    }



}