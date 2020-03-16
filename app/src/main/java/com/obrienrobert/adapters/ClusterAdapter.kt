package com.obrienrobert.adapters

import android.graphics.Color
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
import com.obrienrobert.models.ClusterStore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class ClusterAdapter(private val arrayOfClusters: List<ClusterModel>) :
    RecyclerView.Adapter<ClusterAdapter.ViewHolder>(), Filterable, AnkoLogger {

    var currentActiveCluster: Int = 0

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

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), AnkoLogger {

        fun bind(clusters: List<ClusterModel>, position: Int) {
            this.itemView.findViewById<TextView>(R.id.cluster_name).text =
                clusters[position].clusterName

            repeat(clusters.size) {
                if (clusters[position].isActiveCluster) {
                    itemView.setBackgroundColor(Color.LTGRAY)
                    currentActiveCluster = position
                } else {
                    itemView.setBackgroundColor(Color.WHITE)
                }
            }

            this.itemView.setOnClickListener {
                itemView.setBackgroundColor(Color.LTGRAY)
                clusters[position].isActiveCluster = true
                clusters[currentActiveCluster].isActiveCluster = false
                currentActiveCluster = position
                notifyDataSetChanged()
            }
        }
    }
}