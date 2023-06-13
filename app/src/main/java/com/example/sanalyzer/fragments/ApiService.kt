package com.example.sanalyzer.fragments

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
interface ApiService {
    @Headers("X-RapidAPI-Key: febf774534msh8e71b6842063fe1p172242jsn613f7b24e2ba")
    @GET("stock/v2/get-chart")
    suspend fun getChartData(
        @Query("interval") interval: String,
        @Query("symbol") symbol: String,
        @Query("range") range: String
    ): Response<ChartResponse>
}