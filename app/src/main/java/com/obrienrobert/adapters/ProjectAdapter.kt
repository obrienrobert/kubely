package com.obrienrobert.adapters

import android.content.Intent
import android.graphics.Color
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
import com.obrienrobert.main.Watch
import com.obrienrobert.models.ClusterStore
import io.fabric8.openshift.api.model.Project
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.util.*


class ProjectAdapter(private val projectList: List<Project>) :
    RecyclerView.Adapter<ProjectAdapter.ViewHolder>(), Filterable {

    private var copyOfProjects: List<Project> = projectList.toList()
    override fun getFilter(): Filter = object : Filter() {
        override fun performFiltering(value: CharSequence?): FilterResults {
            val results = FilterResults()
            if (value.isNullOrEmpty()) {
                results.values = projectList
            } else {
                copyOfProjects = projectList.filter {
                    it.metadata.name.contains(value, true)
                }
                results.values = copyOfProjects
            }
            return results
        }

        @Suppress("UNCHECKED_CAST")
        override fun publishResults(value: CharSequence?, results: FilterResults?) {
            copyOfProjects = (results?.values as List<Project>)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.project_card_view, parent, false)
        )

    }

    override fun getItemCount(): Int {
        return copyOfProjects.size
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(copyOfProjects, position)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), AnkoLogger {

        fun bind(projects: List<Project>, position: Int) {
            this.itemView.findViewById<TextView>(R.id.resource_name).text =
                projects[position].metadata.name

            this.itemView.findViewById<ImageView>(R.id.project_icon).setImageResource(R.drawable.project_icon)

            repeat(projects.size) {
                if (projects[position].metadata.name == ClusterStore.getActiveCluster()?.activeNamespace) {
                    itemView.setBackgroundColor(Color.GREEN)
                    //Collections.swap(projects, position, 0)
                } else {
                    itemView.setBackgroundColor(Color.BLACK)
                }
            }

            // Default to the first project in the list if empty
           if(ClusterStore.getActiveCluster()?.activeNamespace.isNullOrEmpty()){
                if(position == 0){
                // Collections.swap(projects, position, 0)
                itemView.setBackgroundColor(Color.GREEN)}
            }

            this.itemView.findViewById<TextView>(R.id.resource_info).text =
                projects[position].metadata.creationTimestamp

            val showIcon: ImageView = itemView.findViewById(R.id.show)
            showIcon.setOnClickListener {
                info { "ImageView clicked!" }
                val intent = Intent(itemView.context, Watch::class.java).putExtra("namespace",  projects[position].metadata.name)
                itemView.context.startActivity(intent)
            }

            this.itemView.setOnClickListener {
                ClusterStore.setActiveNamespace(projects[position].metadata.name)
                itemView.setBackgroundColor(Color.GREEN)
                notifyDataSetChanged()
            }

            }
        }
    }