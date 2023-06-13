package com.example.sanalyzer.fragments
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sanalyzer.R

class ItemAdapter( private val itemList: MutableList<Pair<String, Int>>,
                   private val mainInterface: SOrderInterface
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>(), Filterable {

    private var filteredList: MutableList<Pair<String, Int>> = itemList.toMutableList()
    private var isSearching = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lq45list, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (isSearching) filteredList.size else itemList.size
    }

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView: TextView = itemView.findViewById(R.id.lqname)
        private val stockListImage = itemView.findViewById<ImageView>(R.id.stockImage)

        fun bind(item: Pair<String, Int>) {
            textView.text = item.first
            Glide.with(itemView)
                .load(item.second)
                .into(stockListImage)

            itemView.setOnClickListener {
                mainInterface.click(textView.text.toString())
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                val query = constraint?.toString()?.uppercase()

                filteredList = if (query.isNullOrBlank()) {
                    itemList.toMutableList()
                } else {
                    isSearching = true
                    itemList.filter { item ->
                        item.first.uppercase().contains(query)
                    }.toMutableList()
                }

                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredList = results?.values as? MutableList<Pair<String, Int>> ?: mutableListOf()
                notifyDataSetChanged()
            }
        }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = if (isSearching) filteredList[position] else itemList[position]
        holder.bind(item)
    }
}