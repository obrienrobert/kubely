package com.obrienrobert.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.obrienrobert.main.R
import com.obrienrobert.models.ClusterModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class ClusterAdapter(private val arrayOfClusters: List<ClusterModel>) :
    RecyclerView.Adapter<ClusterAdapter.ViewHolder>(), Filterable, AnkoLogger {

    private var copyOfClusters: List<ClusterModel> = arrayOfClusters.toList()

    override fun getFilter(): Filter = object : Filter() {
        override fun performFiltering(value: CharSequence?): FilterResults {
            val results = FilterResults()
            if (value.isNullOrEmpty()) {
                results.values = arrayOfClusters
            } else {
                copyOfClusters = arrayOfClusters.filter {
                    it.clusterName?.contains(value, true)!!
                }
                results.values = copyOfClusters
            }
            return results
        }

        @Suppress("UNCHECKED_CAST")
        override fun publishResults(value: CharSequence?, results: FilterResults?) {
            copyOfClusters = (results?.values as? List<ClusterModel>).orEmpty()
            notifyDataSetChanged()
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cluster_card_view, parent, false) as CardView
        )
    }

    override fun getItemCount(): Int {

        return copyOfClusters.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(copyOfClusters, position)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view), AnkoLogger {

        fun bind(nodes: List<ClusterModel>, position: Int) {
            this.itemView.findViewById<TextView>(R.id.cluster_name).text =
                nodes[position].clusterName

            this.itemView.setOnClickListener {
                info{ "Clicked item ${nodes[position].clusterName} at $position" }
            }
        }
    }
}