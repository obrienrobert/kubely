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
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class DeploymentAdapter(private val arrayOfDeployments: List<String>) :
    RecyclerView.Adapter<DeploymentAdapter.ViewHolder>(), Filterable {

    private var copyOfDeployments: List<String> = arrayOfDeployments.toList()

    override fun getFilter(): Filter = object : Filter() {
        override fun performFiltering(value: CharSequence?): FilterResults {
            val results = FilterResults()
            if (value.isNullOrEmpty()) {
                results.values = arrayOfDeployments
            } else {
                copyOfDeployments = arrayOfDeployments.filter {
                    it.contains(value, true)
                }
                results.values = copyOfDeployments
            }
            return results
        }

        @Suppress("UNCHECKED_CAST")
        override fun publishResults(value: CharSequence?, results: FilterResults?) {
            copyOfDeployments = (results?.values as? List<String>).orEmpty()
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
        return copyOfDeployments.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(copyOfDeployments[position], position)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view), AnkoLogger {

        fun bind(value: String, position: Int) {
            this.itemView.findViewById<TextView>(R.id.resource_name).text = value
            this.itemView.setOnClickListener {
                info { "Clicked item $value at $position" }
            }

            this.itemView.findViewById<ImageView>(R.id.resource_icon)
                .setImageResource(R.drawable.deployment_icon)
        }
    }
}