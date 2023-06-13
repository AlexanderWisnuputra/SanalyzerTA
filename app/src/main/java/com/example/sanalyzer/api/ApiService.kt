package com.example.sanalyzer.api

import com.example.sanalyzer.data.ChartResponse
import com.example.sanalyzer.data.StockStatistics
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
interface ApiService {
    @Headers("X-RapidAPI-Key: febf774534msh8e71b6842063fe1p172242jsn613f7b24e2ba")
    @GET("stock/v2/get-chart")
    fun getChart(
        @Query("symbol") symbol: String,
        @Query("interval") interval: String,
        @Query("range") range: String
    ): Call<ChartResponse>

    @Headers("X-RapidAPI-Key: febf774534msh8e71b6842063fe1p172242jsn613f7b24e2ba")
    @GET("stock/v2/get-statistics")
    fun getStockData(
        @Query("symbol") symbol: String
    ): Call<StockStatistics>

    @Headers("X-RapidAPI-Key: febf774534msh8e71b6842063fe1p172242jsn613f7b24e2ba")
    @GET("stock/v2/get-summary")
    fun getsummary(
        @Query("symbol") symbol: String
    ): Call<StockStatistics>
}
