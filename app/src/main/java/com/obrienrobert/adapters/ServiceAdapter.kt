package com.obrienrobert.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.obrienrobert.main.R

class ServiceAdapter(private val arrayOfStrings: List<String>) :
    RecyclerView.Adapter<ServiceAdapter.ViewHolder>(), Filterable {

    private var copyOfStrings: List<String> = arrayOfStrings.toList()

    override fun getFilter(): Filter =
        object : Filter() {
            override fun performFiltering(value: CharSequence?): FilterResults {
                val results = FilterResults()
                if (value.isNullOrEmpty()) {
                    results.values = arrayOfStrings
                } else {
                    copyOfStrings = arrayOfStrings.filter {
                        it.contains(value, true)
                    }
                    results.values = copyOfStrings
                }
                return results
            }

            override fun publishResults(value: CharSequence?, results: FilterResults?) {
                copyOfStrings = (results?.values as? List<String>).orEmpty()
                notifyDataSetChanged()
            }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.card_view, parent, false) as CardView
        )
    }

    override fun getItemCount(): Int {
        return copyOfStrings.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(copyOfStrings[position], position)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(value: String, position: Int) {
            this.itemView.findViewById<TextView>(R.id.info_text).text = value
            this.itemView.setOnClickListener {
                Log.e("CLICK", "Clicked item $value at $position")
            }
        }
    }
}