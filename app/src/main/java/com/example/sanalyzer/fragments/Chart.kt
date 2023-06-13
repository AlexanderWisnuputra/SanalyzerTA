package com.example.sanalyzer.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.sanalyzer.R
import com.example.sanalyzer.databinding.FragmentChartBinding
import com.example.sanalyzer.databinding.FragmentDetailBinding

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

            val data = binding.textView.text.toString()
            sharedPreferences.edit().putString("key", data).apply()

        }
    }
}