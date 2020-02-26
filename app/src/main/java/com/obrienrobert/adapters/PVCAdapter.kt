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

class PVCAdapter(private val arrayOfPVCs: List<String>) :
    RecyclerView.Adapter<PVCAdapter.ViewHolder>(), Filterable {

    private var copyOfPVCs: List<String> = arrayOfPVCs.toList()

    override fun getFilter(): Filter = object : Filter() {
        override fun performFiltering(value: CharSequence?): FilterResults {
            val results = FilterResults()
            if (value.isNullOrEmpty()) {
                results.values = arrayOfPVCs
            } else {
                copyOfPVCs = arrayOfPVCs.filter {
                    it.contains(value, true)
                }
                results.values = copyOfPVCs
            }
            return results
        }

        override fun publishResults(value: CharSequence?, results: FilterResults?) {
            copyOfPVCs = (results?.values as? List<String>).orEmpty()
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
        return copyOfPVCs.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(copyOfPVCs[position], position)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(value: String, position: Int) {
            this.itemView.findViewById<TextView>(R.id.resource_name).text = value
            this.itemView.setOnClickListener {
                Log.e("CLICK", "Clicked item $value at $position")
            }
        }
    }
}