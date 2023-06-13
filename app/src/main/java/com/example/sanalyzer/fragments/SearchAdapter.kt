package com.example.sanalyzer.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sanalyzer.R

class SearchAdapter(private val dataList: List<String>) :
    RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private var filteredList: List<String> = dataList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.lq45list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = filteredList[position]
        holder.textView.text = item
    }

    override fun getItemCount(): Int = filteredList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(android.R.id.text1)
    }

    fun filter(query: String) {
        filteredList = dataList.filter { it.contains(query, true) }
        notifyDataSetChanged()
    }
}