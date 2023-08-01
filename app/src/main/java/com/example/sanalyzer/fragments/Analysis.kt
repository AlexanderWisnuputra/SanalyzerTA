package com.example.sanalyzer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.sanalyzer.R
import com.example.sanalyzer.databinding.FragmentAnalysisBinding
import net.sourceforge.jFuzzyLogic.FIS
import kotlin.math.round


class Analysis : Fragment() {
    private lateinit var binding: FragmentAnalysisBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val FragmentAnalysisBinding = FragmentAnalysisBinding.inflate(inflater, container, false)
        binding = FragmentAnalysisBinding
        return FragmentAnalysisBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val code = arguments?.getString("codes")
        val pbv = arguments?.getString("pbv")
        val roe = arguments?.getString("roe")
        val div = arguments?.getString("div")
        val low = arguments?.getString("low")
        val high = arguments?.getString("high")
        val volume = arguments?.getString("volume")
        val volume2 = arguments?.getString("volume2")
        val vol3 = arguments?.getString("volume3")
        val price = arguments?.getString("price")
        val nf = arguments?.getString("nf")
        val percentage = arguments?.getString("percentage2")
        val change = arguments?.getString("change")
        val min = (volume!!.toDouble())
        val max = (vol3!!.toDouble())
        val comb = max - min
        val cvol = calculatePercentage(comb, vol3.toDouble())
        val differ = high!!.toDouble() - low!!.toDouble()
        val differ2 =  price!!.toDouble() - low.toDouble()
        val dfer = calculatePercentage(differ2, differ)
        val cvol1 = rounding(cvol).toString()
        val dfer1 = rounding(dfer).toString()
        binding.volume.text = nf.toString()
        binding.textView.text = price.toString()
        binding.StockName22.text = code
        binding.price.text = change.toString()
        binding.volume.text = volume2.toString()
        binding.textView.text = price.toString()
        binding.percentage.text = percentage.toString()
        binding.textView00.text = div.toString()
        binding.textView11.text = rounding(pbv!!.toDouble()).toString()
        binding.textView44.text = rounding(roe!!.toDouble()).toString()
        binding.textView55.text = "$cvol1 %"
        binding.textView66.text = "$dfer1"

        if (change!!.toDouble() > 0) {
            binding.imageView.setImageResource(R.drawable.up)
        } else if (change!!.toDouble() == 0.0) {
            binding.imageView.visibility = View.INVISIBLE
        } else {
            binding.imageView.setImageResource(R.drawable.down)
        }
        main(div!!.toDouble(), pbv!!.toDouble(), roe!!.toDouble(), cvol, dfer)
    }

    fun main(a:Double, b: Double, c:Double, d:Double, e:Double) {
        val inputStream = context!!.assets.open("rekomendasi.fcl")
        val fuzzySystem = FIS.load(inputStream, true)
        if (fuzzySystem == null) {
            binding.textView12.text = "Fail"
            return
        }
        fuzzySystem.setVariable("dividen", a)
        fuzzySystem.setVariable("pbv", b)
        fuzzySystem.setVariable("roe", c)
        fuzzySystem.setVariable("fibo", d)
        fuzzySystem.setVariable("vchange", e)
        fuzzySystem.evaluate()
        val req = fuzzySystem.getVariable("rekomendasi").value
        binding.textView15.text = rounding(req).toString()
        if (req >= 60){
            binding.textView13.text = "Beli"
        }
        else
        {
            binding.textView13.text = "Tidak Beli"
        }
    }

    fun rounding(number: Double): Double {
        return round(number * 100) / 100.0
    }
    fun calculatePercentage(a: Double, b: Double): Double {
        if (b == 0.0) {
            throw IllegalArgumentException("Division by zero is not allowed.")
        }
        if (a == 0.0) {
            throw IllegalArgumentException("Division by zero is not allowed.")
        }
        return (a / b)
    }

}