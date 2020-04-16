package com.obrienrobert.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.obrienrobert.main.R
import io.fabric8.kubernetes.api.model.Pod
import org.jetbrains.anko.AnkoLogger

class PodAdapter(private val arrayOfPods: List<Pod>) :
    RecyclerView.Adapter<PodAdapter.ViewHolder>(), Filterable {

    private var copyOfPods: List<Pod> = arrayOfPods.toList()

    override fun getFilter(): Filter = object : Filter() {
        override fun performFiltering(value: CharSequence?): FilterResults {
            val results = FilterResults()
            if (value.isNullOrEmpty()) {
                results.values = arrayOfPods
            } else {
                copyOfPods = arrayOfPods.filter {
                    it.metadata.name.contains(value, true)
                }
                results.values = copyOfPods
            }
            return results
        }

        @Suppress("UNCHECKED_CAST")
        override fun publishResults(value: CharSequence?, results: FilterResults?) {
            copyOfPods = (results?.values as? List<Pod>).orEmpty()
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
        return copyOfPods.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(copyOfPods, position)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view), AnkoLogger {

        fun bind(pods: List<Pod>, position: Int) {
            this.itemView.findViewById<TextView>(R.id.resource_name).text =
                pods[position].metadata.name

            this.itemView.findViewById<TextView>(R.id.resource_info).text =
                pods[position].metadata.namespace

            this.itemView.findViewById<ImageView>(R.id.resource_icon)
                .setImageResource(R.drawable.pod_icon)
        }
    }
}