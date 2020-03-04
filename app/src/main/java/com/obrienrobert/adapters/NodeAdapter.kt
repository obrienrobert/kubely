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
import io.fabric8.kubernetes.api.model.Node

class NodeAdapter(private val arrayOfNodes: List<Node>) :
    RecyclerView.Adapter<NodeAdapter.ViewHolder>(), Filterable {

    private var copyOfNodes: List<Node> = arrayOfNodes.toList()

    override fun getFilter(): Filter = object : Filter() {
        override fun performFiltering(value: CharSequence?): FilterResults {
            val results = FilterResults()
            if (value.isNullOrEmpty()) {
                results.values = arrayOfNodes
            } else {
                copyOfNodes = arrayOfNodes.filter {
                    it.metadata.name.contains(value, true)
                }
                results.values = copyOfNodes
            }
            return results
        }

        @Suppress("UNCHECKED_CAST")
        override fun publishResults(value: CharSequence?, results: FilterResults?) {
            copyOfNodes = (results?.values as? List<Node>).orEmpty()
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
        return copyOfNodes.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(copyOfNodes, position)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(nodes: List<Node>, position: Int) {
            this.itemView.findViewById<TextView>(R.id.resource_name).text =
                nodes[position].metadata.name

            Log.d("TEST: ", nodes[position].metadata.name)

            this.itemView.setOnClickListener {
                Log.e("CLICK", "Clicked item ${nodes[position].metadata.name} at $position")
            }
        }
    }
}