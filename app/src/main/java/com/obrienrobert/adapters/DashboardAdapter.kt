package com.obrienrobert.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.obrienrobert.main.R
import com.obrienrobert.models.ClusterModel
import com.obrienrobert.models.ClusterStore
import org.jetbrains.anko.AnkoLogger

class DashboardAdapter :
    RecyclerView.Adapter<DashboardAdapter.ViewHolder>(), AnkoLogger {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.event_card_view, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return ClusterStore.listOfClusters.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), AnkoLogger {

        fun bind(clusters: List<ClusterModel>, position: Int) {

        }
    }
}