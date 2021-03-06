package com.obrienrobert.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.obrienrobert.main.R
import kotlinx.android.synthetic.main.resource_card_view.view.*
import org.jetbrains.anko.AnkoLogger

class RouteAdapter(private val arrayOfRoutes: List<String>) :
    RecyclerView.Adapter<RouteAdapter.ViewHolder>(), Filterable {

    private var copyOfRoutes: List<String> = arrayOfRoutes.toList()

    override fun getFilter(): Filter = object : Filter() {
        override fun performFiltering(value: CharSequence?): FilterResults {
            val results = FilterResults()
            if (value.isNullOrEmpty()) {
                results.values = arrayOfRoutes
            } else {
                copyOfRoutes = arrayOfRoutes.filter {
                    it.contains(value, true)
                }
                results.values = copyOfRoutes
            }
            return results
        }

        @Suppress("UNCHECKED_CAST")
        override fun publishResults(value: CharSequence?, results: FilterResults?) {
            copyOfRoutes = (results?.values as? List<String>).orEmpty()
            notifyDataSetChanged()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.resource_card_view, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return copyOfRoutes.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(copyOfRoutes[position], position)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view), AnkoLogger {

        fun bind(value: String, position: Int) {
            itemView.resource_name.text = value

            itemView.resource_icon.setImageResource(R.drawable.route_icon)
        }
    }
}