package com.example.sanalyzer.fragments

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sanalyzer.R
import com.example.sanalyzer.adapter.SearchStockAdapter
import com.example.sanalyzer.adapter.SOrderInterface
import com.example.sanalyzer.api.YahooFinanceApiClient
import com.example.sanalyzer.databinding.FragmentHomeBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class Home : Fragment(), SOrderInterface {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var searchStockAdapter: SearchStockAdapter
    private lateinit var sharedPreferences: SharedPreferences
    private val searchList: MutableList<Pair<String, Int>> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val FragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        binding = FragmentHomeBinding
        return FragmentHomeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        recyclerView = binding.LQ45
        searchStockAdapter = SearchStockAdapter(searchList, this)
        searchView = binding.LQ45Search
        recyclerView.adapter = searchStockAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
      filldata()
      search()
      getData()
    }
    private fun getData(){
        val yahooFinanceApiClient = YahooFinanceApiClient()
        yahooFinanceApiClient.getChart("^JKSE", "30m", "1d") { timestamp, closePrices ->
            val lineChart = binding.IHSG
            createLineChart(timestamp, closePrices, lineChart)
        }
    }
    private fun createLineChart(timestamp: List<Long>, closePrices: List<Float>, chart: LineChart) {
        val entries = mutableListOf<Entry>()
        val dateFormatterA = SimpleDateFormat("dd/MM", Locale.getDefault())
        dateFormatterA.timeZone = TimeZone.getTimeZone("GMT+7")

        for (i in timestamp.indices) {
            if (closePrices[i]!= null) {
            val closePrice = closePrices[i]
                entries.add(Entry(timestamp[i].toFloat(), closePrice))
            }
        }

        val xAxis = chart.xAxis
        val dataSet = LineDataSet(entries,"")
        val lineData = LineData(dataSet)
        xAxis.position = XAxis.XAxisPosition.BOTTOM;
        xAxis.valueFormatter = TimestampAxisValueFormatter()
        dataSet.setDrawFilled(true)
        dataSet.fillColor = Color.BLUE
        chart.setBackgroundColor(Color.WHITE)
        chart.setDragEnabled(true)
        chart.setPinchZoom(false)
        chart.setScaleEnabled(true)
        chart.axisRight.setDrawLabels(false)
        chart.description.isEnabled = false
        chart.legend.isEnabled = false
        chart.axisRight.isEnabled = false
        chart.setBorderColor(255)
        chart.setDrawGridBackground(false)
        chart.xAxis.setDrawGridLines(false)
        chart.axisLeft.setDrawGridLines(false)
        chart.axisRight.setDrawGridLines(false)
        chart.data = lineData
        chart.invalidate()
    }

    class TimestampAxisValueFormatter : ValueFormatter() {
        private val dateFormatter = SimpleDateFormat("HH:mm", Locale.getDefault())

        init {
            dateFormatter.timeZone = TimeZone.getTimeZone("GMT+7")
        }

        override fun getFormattedValue(value: Float): String {
            val dateInMillis = (value * 1000).toLong()
            return dateFormatter.format(Date(dateInMillis))
        }
    }

    private fun search(){
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchStockAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchStockAdapter.filter.filter(newText)
                return false
            }
        })
    }

    private fun add(name: String, intValue: Int) {
        searchList.add(Pair(name, intValue))
    }

    private fun filldata(){
    searchList.clear()
    add("ADHI", R.drawable.aces)
    add("ADRO", R.drawable.adro)
    add("AKRA", R.drawable.akra)
    add("ASRI", R.drawable.amrt)
    add("ANTM", R.drawable.antm)
    add("ARTO", R.drawable.arto)
    add("ASII", R.drawable.asii)
    add("BBCA", R.drawable.bbca)
    add("BBNI", R.drawable.bbni)
    add("BBRI", R.drawable.bbri)
    add("BBTN", R.drawable.bbtn)
    add("BMRI", R.drawable.bmri)
    add("BRIS", R.drawable.bris)
    add("BRPT", R.drawable.brpt)
    add("BUKA", R.drawable.buka)
    add("CPIN", R.drawable.cpin)
    add("EMTK", R.drawable.emtk)
    add("ESSA", R.drawable.essa)
    add("EXCL", R.drawable.excl)
    add("GOTO", R.drawable.gojo)
    add("HRUM", R.drawable.hrum)
    add("ICBP", R.drawable.icbp)
    add("INCO", R.drawable.inco)
    add("INDF", R.drawable.indf)
    add("INDY", R.drawable.indy)
    add("INKP", R.drawable.inkp)
    add("INTP", R.drawable.intp)
    add("ITMG", R.drawable.itmg)
    add("JPFA", R.drawable.jpfa)
    add("KLBF", R.drawable.klbf)
    add("MDKA", R.drawable.mdka)
    add("MEDC", R.drawable.medc)
    add("PGAS", R.drawable.pgas)
    add("PTBA", R.drawable.ptba)
    add("SCMA", R.drawable.scma)
    add("SIDO", R.drawable.sido)
    add("SMGR", R.drawable.smgr)
    add("SRTG", R.drawable.srtg)
    add("TBIG", R.drawable.tbig)
    add("TINS", R.drawable.tins)
    add("TLKM", R.drawable.tlkm)
    add("TOWR", R.drawable.towr)
    add("TPIA", R.drawable.tpia)
    add("UNTR", R.drawable.untr)
    add("UNVR", R.drawable.unvr)
}

    override fun click(item: String) {
        val data = item
        sharedPreferences.edit().putString("key", data).apply()
        findNavController().navigate(R.id.action_home_to_detail)
    }
}

