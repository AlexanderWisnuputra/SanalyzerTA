package com.example.sanalyzer.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.sanalyzer.R
import com.example.sanalyzer.api.YahooFinanceApiClient
import com.example.sanalyzer.data.StockStatistics
import com.example.sanalyzer.databinding.FragmentDetailBinding
import com.example.sanalyzer.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Retrofit


class Detail : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val FragmentDetailBinding = FragmentDetailBinding.inflate(inflater, container, false)
        binding = FragmentDetailBinding
        return FragmentDetailBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

        val code = sharedPreferences.getString("key","ADHI")

        binding.StockName2.text = code
        val stockcode = "$code.JK"
        getStatistic(stockcode)

        getsummary(stockcode)

        binding.button5.setOnClickListener {
            findNavController().navigate(R.id.action_detail_to_analysis)
        }
    }

    private fun getStatistic(string: String) {
        val yahooFinanceApiClient = YahooFinanceApiClient()

        val call = yahooFinanceApiClient.apiService.getStockData(string)

        call.enqueue(object : retrofit2.Callback<StockStatistics> {
            override fun onResponse(
                call: Call<StockStatistics>,
                response: retrofit2.Response<StockStatistics>
            ) {
                if (response.isSuccessful) {
                    val stockStatistics = response.body()
                    if (stockStatistics != null) {
                        val result = response.body()
                        binding.textView00.text = result?.summaryDetail?.fiftyTwoWeekHigh?.fmt.toString()
                        binding.textView11.text = result?.summaryDetail?.fiftyTwoWeekLow?.fmt.toString()
                        binding.textView22.text = result?.summaryDetail?.dividendYield?.fmt.toString()
                        binding.textView33.text = result?.financialData?.returnOnAssets?.fmt.toString()
                        binding.textView44.text = result?.financialData?.returnOnEquity?.fmt.toString()
                        binding.textView55.text = result?.defaultKeyStatistics?.bookValue?.fmt.toString()
                        binding.textView77.text = result?.defaultKeyStatistics?.trailingEps?.fmt.toString()
                        binding.textView88.text = result?.summaryDetail?.marketCap?.fmt.toString()
                        fun click(item: String) {
                            val data = result?.defaultKeyStatistics?.priceToBook.toString()
                            val mBundle = Bundle()
                            mBundle.putString("code", data)

                            findNavController().navigate(R.id.action_home_to_detail, mBundle)
                        }
                    } else {
                        println("API request failed with response code: ${response.code()}")
                    }
                }
            }

            override fun onFailure(call: Call<StockStatistics>, t: Throwable) {
                println("API request failed: ${t.message}")
            }
        })
    }

    private fun getsummary(string: String) {
        val yahooFinanceApiClient = YahooFinanceApiClient()

        val call = yahooFinanceApiClient.apiService.getsummary(string)

        call.enqueue(object : retrofit2.Callback<StockStatistics> {
            override fun onResponse(
                call: Call<StockStatistics>,
                response: retrofit2.Response<StockStatistics>
            ) {
                if (response.isSuccessful) {
                        val stockStatistics = response.body()
                        if (stockStatistics != null) {
                            val result = response.body()
                            binding.textView.text = result?.price?.regularMarketPrice?.fmt
                            binding.price.text = result?.price?.regularMarketChange?.fmt
                            binding.textView66.text =
                                result?.financialData?.grossProfits?.fmt.toString()
                            binding.percentage.text = result?.price?.regularMarketChangePercent?.fmt
                            binding.volume.text = result?.summaryDetail?.volume?.fmt.toString()
                            binding.industri.text = result?.summaryProfile?.sector.toString()
                            if (result?.price?.regularMarketChange?.raw!! > 0) {
                                binding.imageView.setImageResource(R.drawable.up)
                            } else if (result?.price?.regularMarketChange?.raw!! == 0.0) {
                                binding.imageView.visibility = View.INVISIBLE

                            } else {
                                binding.imageView.setImageResource(R.drawable.down)

                            }

                            binding.button4.setOnClickListener() {
                                val mBundle = Bundle()
                                val data = binding.StockName2.text.toString()
                                mBundle.putString("price", result.price.regularMarketPrice.fmt)
                                mBundle.putString("change", result.price.regularMarketChange.fmt)
                                mBundle.putString("percentage", result.price.regularMarketChangePercent.fmt)
                                mBundle.putString("volume", result.summaryDetail.volume.fmt.toString())
                                mBundle.putString("codes", data)
                                findNavController().navigate(R.id.action_detail_to_chart, mBundle)
                            }

                        } else {
                            println("API request failed with response code: ${response.code()}")
                        }
                    }

            }
            override fun onFailure(call: Call<StockStatistics>, t: Throwable) {
                println("API request failed: ${t.message}")
            }
        })
    }
}
