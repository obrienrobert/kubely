package com.obrienrobert.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.obrienrobert.main.R
import io.fabric8.kubernetes.api.model.Event
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class EventAdapter(private val arrayOfEvents: List<Event>) :
    RecyclerView.Adapter<EventAdapter.ViewHolder>(), Filterable {

    private var copyOfEvents: List<Event> = arrayOfEvents.toList()

    override fun getFilter(): Filter = object : Filter() {
        override fun performFiltering(value: CharSequence?): FilterResults {
            val results = FilterResults()
            if (value.isNullOrEmpty()) {
                results.values = arrayOfEvents
            } else {
                copyOfEvents = arrayOfEvents.filter {
                    it.metadata.name.contains(value, true)
                }
                results.values = copyOfEvents
            }
            return results
        }

        @Suppress("UNCHECKED_CAST")
        override fun publishResults(value: CharSequence?, results: FilterResults?) {
            copyOfEvents = (results?.values as? List<Event>).orEmpty()
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.event_card_view, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return copyOfEvents.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(copyOfEvents, position)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view), AnkoLogger {

        fun bind(event: List<Event>, position: Int) {
            this.itemView.findViewById<TextView>(R.id.timestamp).text =
                event[position].lastTimestamp

            this.itemView.findViewById<TextView>(R.id.message).text =
                event[position].message

            this.itemView.findViewById<TextView>(R.id.reason).text =
                event[position].reason
        }
    }
}