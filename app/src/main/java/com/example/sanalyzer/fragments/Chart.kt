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
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.data.Entry
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
        val price = arguments?.getString("price")
        val change = arguments?.getString("change")
        val pbv = arguments?.getString("pbv")
        val roe = arguments?.getString("roe")
        val div = arguments?.getString("div")
        val low = arguments?.getString("low")
        val high = arguments?.getString("high")
        val vol3 = arguments?.getString("volume3")
        val nf = arguments?.getString("nf")
        val percentage2 = arguments?.getString("percentage2")

        binding.textView3.text = code
        binding.textView5.text = change.toString()
        binding.textView2.text = nf.toString()
        binding.textView.text = price.toString()
        binding.textView4.text = percentage2.toString()
        val volume2 = arguments?.getString("volume2")

        if (binding.textView5.text.toString().toDouble() > 0) {
            binding.imageView.setImageResource(R.drawable.up)
        } else if (binding.textView5.text.toString().toDouble() == 0.0) {
            binding.imageView.visibility = View.INVISIBLE
        } else {
            binding.imageView.setImageResource(R.drawable.down)
        }

        binding.button5.setOnClickListener {
            val mBundle = Bundle()
            mBundle.putString("codes", binding.textView3.text.toString())
            mBundle.putString("pbv", pbv.toString())
            mBundle.putString("roe", roe.toString())
            mBundle.putString("div", div.toString())
            mBundle.putString("low", low.toString())
            mBundle.putString("high", high.toString())
            mBundle.putString("volume2", volume2.toString())
            mBundle.putString("volume3", vol3.toString())
            mBundle.putString("price", price.toString())
            mBundle.putString("nf", nf.toString())
            mBundle.putString("percentage2", percentage2.toString())
            mBundle.putString("change", change.toString())
            mBundle.putString("nf", nf.toString())
            findNavController().navigate(R.id.action_chart_to_analysis, mBundle)
        }
        binding.button7.setOnClickListener {
            findNavController().navigate(R.id.action_chart_to_detail)
            val data = binding.textView3.text.toString()
            sharedPreferences.edit().putString("key", data).apply()
        }
        getcandle(binding.textView3.text.toString() +".JK")

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
            if (highPrice[i] != null && lowPrice[i] != null && openPrice[i] != null && closePrice[i] != null) {
                val entry = CandleEntry(
                    i.toFloat(),
                    highPrice[i]!!,
                    lowPrice[i]!!,
                    openPrice[i]!!,
                    closePrice[i]!!
                )
                entries.add(entry)
            }
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
        dataSet.setDrawHighlightIndicators(true)
        dataSet.highLightColor = Color.YELLOW

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