package com.example.sanalyzer.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sanalyzer.databinding.FragmentAnalysisBinding

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
        val code = arguments?.getString("code")
    }
}