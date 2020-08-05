package com.obrienrobert.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.obrienrobert.main.R
import com.obrienrobert.models.ClusterModel
import kotlinx.android.synthetic.main.cluster_card_view.view.*
import org.jetbrains.anko.AnkoLogger

class ClusterAdapter(private val arrayOfClusters: List<ClusterModel>) :
    RecyclerView.Adapter<ClusterAdapter.ViewHolder>(), Filterable {

    private var copyOfClusters: List<ClusterModel> = arrayOfClusters

    override fun getFilter(): Filter = object : Filter() {
        override fun performFiltering(value: CharSequence?): FilterResults {
            val results = FilterResults()
            if (value.isNullOrEmpty()) {
                results.values = arrayOfClusters
            } else {
                copyOfClusters = arrayOfClusters.filter {
                    it.clusterName == value
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
                .inflate(R.layout.cluster_card_view, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return copyOfClusters.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(copyOfClusters[position], position)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view), AnkoLogger {

        fun bind(value: ClusterModel, position: Int) {
            itemView.cluster_url.text = value.masterURL.toString()

            itemView.cluster_icon.setImageResource(R.drawable.cluster_icon)
        }
    }
}