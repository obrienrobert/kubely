package com.obrienrobert.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.obrienrobert.main.R
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class SecretAdapter(private val arrayOfSecrets: List<String>) :
    RecyclerView.Adapter<SecretAdapter.ViewHolder>(), Filterable {

    private var copyOfSecrets: List<String> = arrayOfSecrets.toList()

    override fun getFilter(): Filter = object : Filter() {
        override fun performFiltering(value: CharSequence?): FilterResults {
            val results = FilterResults()
            if (value.isNullOrEmpty()) {
                results.values = arrayOfSecrets
            } else {
                copyOfSecrets = arrayOfSecrets.filter {
                    it.contains(value, true)
                }
                results.values = copyOfSecrets
            }
            return results
        }

        @Suppress("UNCHECKED_CAST")
        override fun publishResults(value: CharSequence?, results: FilterResults?) {
            copyOfSecrets = (results?.values as? List<String>).orEmpty()
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
        return copyOfSecrets.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(copyOfSecrets[position], position)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view), AnkoLogger {

        fun bind(value: String, position: Int) {
            this.itemView.findViewById<TextView>(R.id.resource_name).text = value
            this.itemView.setOnClickListener {
                info{ "Clicked item $value at $position" }
            }

            this.itemView.findViewById<ImageView>(R.id.resource_icon).setImageResource(R.drawable.secret_icon)
        }
    }
}