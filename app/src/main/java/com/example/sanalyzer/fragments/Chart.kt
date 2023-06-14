package com.example.sanalyzer.fragments

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.sanalyzer.R

import com.example.sanalyzer.api.YahooFinanceApiClient
import com.github.mikephil.charting.formatter.ValueFormatter

import com.example.sanalyzer.databinding.FragmentChartBinding
import com.github.mikephil.charting.charts.CandleStickChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class Chart : Fragment() {
    private lateinit var binding: FragmentChartBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val FragmentChartBinding = FragmentChartBinding.inflate(inflater, container, false)
        binding = FragmentChartBinding
        return FragmentChartBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)

        val code = arguments?.getString("codes")
        getcandle("$code.JK")

        val price = arguments?.getString("price")
        val volume = arguments?.getString("volume")
        val percentage = arguments?.getString("percentage")
        val change = arguments?.getString("change")
        binding.textView3.text = code
        binding.textView5.text = change.toString()
        binding.textView2.text = volume.toString()
        binding.textView.text = price.toString()
        binding.textView4.text = percentage.toString()

        if (binding.textView5.text.toString().toDouble() > 0) {
            binding.imageView.setImageResource(R.drawable.up)
        } else if (binding.textView5.text.toString().toDouble() == 0.0) {
            binding.imageView.visibility = View.INVISIBLE
        } else {
            binding.imageView.setImageResource(R.drawable.down)
        }

        binding.button5.setOnClickListener {
            val mBundle = Bundle()
            mBundle.putString("code", binding.textView3.text.toString())
            findNavController().navigate(R.id.action_chart_to_analysis, mBundle)
        }
        binding.button7.setOnClickListener {
            findNavController().navigate(R.id.action_chart_to_detail)

            val data = binding.textView3.text.toString()
            sharedPreferences.edit().putString("key", data).apply()

        }
    }


    fun getcandle(string: String) {
        val yahooFinanceApiClient = YahooFinanceApiClient()
        yahooFinanceApiClient.candlestick(string, "1d", "1y")
        { timestamp, openPrice, highPrice, lowPrice, closePrice ->
            val Candlestick = binding.button2

            createCandlestick(timestamp, openPrice, highPrice, lowPrice, closePrice, Candlestick)
        }
    }

    fun createCandlestick(
        timestamp: List<Long>,
        openPrice: List<Float>,
        highPrice: List<Float>,
        lowPrice: List<Float>,
        closePrice: List<Float>,
        chart: CandleStickChart
    ) {
        val entries = mutableListOf<CandleEntry>()
        val dateFormatter = SimpleDateFormat("dd/MM", Locale.getDefault())
        dateFormatter.timeZone = TimeZone.getTimeZone("GMT+7")

        for (i in timestamp.indices) {
            val entry = CandleEntry(
                i.toFloat(),
                highPrice[i],
                lowPrice[i],
                openPrice[i],
                closePrice[i]
            )
            entries.add(entry)
        }

        val dataSet = CandleDataSet(entries, "Candlestick Data")
        val candleData = CandleData(dataSet)

        chart.data = candleData
        chart.invalidate()

        chart.description.isEnabled = false
        chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chart.legend.isEnabled = false
        dataSet.axisDependency = YAxis.AxisDependency.LEFT
        dataSet.shadowColor = Color.BLACK
        dataSet.shadowWidth = 0.7f
        dataSet.decreasingColor = Color.RED
        dataSet.color = Color.rgb(80, 80, 80)
        dataSet.decreasingPaintStyle = Paint.Style.FILL
        dataSet.increasingColor = Color.BLUE
        dataSet.increasingPaintStyle = Paint.Style.FILL
        dataSet.neutralColor = Color.BLACK
        chart.setDrawGridBackground(true)
        chart.setBackgroundColor(Color.WHITE)

        // Set custom X-axis labels
        chart.xAxis.valueFormatter = object : ValueFormatter() {
            override fun getFormattedValue(value: Float): String {
                val index = value.toInt()
                return if (index >= 0 && index < timestamp.size) {
                    val dateInMillis = timestamp[index] * 1000 // Convert seconds to milliseconds
                    dateFormatter.format(Date(dateInMillis))
                } else {
                    ""
                }
            }
        }

        // Enable highlighting of highest and lowest values
        dataSet.setDrawHighlightIndicators(true)
        dataSet.highLightColor = Color.YELLOW

        // Enable value highlighting on click
        chart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onValueSelected(e: Entry?, h: Highlight?) {
                chart.highlightValue(h)
            }

            override fun onNothingSelected() {
                chart.highlightValue(null)
            }
        })
    }
}