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
import io.fabric8.kubernetes.api.model.Service

class ServiceAdapter(private val arrayOfServices: List<Service>) :
    RecyclerView.Adapter<ServiceAdapter.ViewHolder>(), Filterable {

    private var copyOfServices: List<Service> = arrayOfServices.toList()

    override fun getFilter(): Filter = object : Filter() {
        override fun performFiltering(value: CharSequence?): FilterResults {
            val results = FilterResults()
            if (value.isNullOrEmpty()) results.values = arrayOfServices
            else {
                copyOfServices = arrayOfServices.filter {
                    it.metadata.name.contains(value, true)
                }
                results.values = copyOfServices
            }
            return results
        }

        @Suppress("UNCHECKED_CAST")
        override fun publishResults(value: CharSequence?, results: FilterResults?) {
            copyOfServices = (results?.values as? List<Service>).orEmpty()
            notifyDataSetChanged()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.resource_card_view, parent, false) as CardView
        )
    }

    override fun getItemCount(): Int {
        return copyOfServices.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(copyOfServices, position)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(services: List<Service>, position: Int) {
            this.itemView.findViewById<TextView>(R.id.resource_name).text =
                services[position].metadata.name

            this.itemView.setOnClickListener {
                Log.e("CLICK", "Clicked item ${services[position].metadata.name} at $position")
            }
        }
    }
}