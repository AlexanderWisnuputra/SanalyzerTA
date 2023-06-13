package com.example.sanalyzer.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.sanalyzer.R
import com.example.sanalyzer.databinding.FragmentDetailBinding
import com.example.sanalyzer.databinding.FragmentHomeBinding


class Detail : Fragment() {
    private lateinit var binding: FragmentDetailBinding

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
        val code = arguments?.getString("code")
        binding.StockName2.text = code
        binding.button4.setOnClickListener {
            findNavController().navigate(R.id.action_detail_to_chart)

        }
    }
}