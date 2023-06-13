package com.example.sanalyzer.fragments

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
import com.example.sanalyzer.databinding.FragmentHomeBinding
import okhttp3.internal.notify
import okhttp3.internal.notifyAll


class Home : Fragment(),SOrderInterface {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var itemAdapter: ItemAdapter
    private val itemList: MutableList<Pair<String, Int>> = mutableListOf()

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

        recyclerView = binding.LQ45
        itemAdapter = ItemAdapter(itemList, this)
        searchView = binding.countrySearch
        recyclerView.adapter = itemAdapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        filldata()
        search()

    }

    private fun search(){
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                itemAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                itemAdapter.filter.filter(newText)
                return false
            }
        })
    }

    private fun add(name: String, intValue: Int) {
        itemList.add(Pair(name, intValue))
    }

    private fun filldata(){
    itemList.clear()
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
    itemAdapter.notifyDataSetChanged()
}

    override fun click(item: String) {
        val data = item
        val mBundle = Bundle()
        mBundle.putString("code", data)

        findNavController().navigate(R.id.action_home_to_detail, mBundle)
    }
}

