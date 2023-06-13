package com.example.sanalyzer.fragments

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
        val code = arguments?.getString("code")
        binding.textView3.text = code
        binding.button5.setOnClickListener {
            findNavController().navigate(R.id.action_chart_to_analysis)
        }
        binding.button7.setOnClickListener {
            findNavController().navigate(R.id.action_chart_to_detail)

        }
    }
}