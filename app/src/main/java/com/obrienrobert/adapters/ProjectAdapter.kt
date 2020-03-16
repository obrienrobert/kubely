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
import com.obrienrobert.client.ActiveNamespace
import com.obrienrobert.main.R
import com.obrienrobert.main.Watch
import io.fabric8.openshift.api.model.Project
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class ProjectAdapter(private val projectList: List<Project>) :
    RecyclerView.Adapter<ProjectAdapter.ViewHolder>(), Filterable {

    var currentActiveNamespace: Int = 0

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
                .inflate(R.layout.project_card_view, parent, false) as CardView
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


            repeat(projects.size) {
                if (projects[position].metadata.name == ActiveNamespace.currentActiveNamespace) {
                    itemView.setBackgroundColor(Color.LTGRAY)
                } else {
                    itemView.setBackgroundColor(Color.WHITE)
                }
            }

            this.itemView.setOnClickListener {
                ActiveNamespace.currentActiveNamespace = projects[position].metadata.name
                info { "Clicked item ${projects[position].metadata.name} at $position" }
                itemView.setBackgroundColor(Color.LTGRAY)
                info { "Testing: " + ActiveNamespace.currentActiveNamespace }
                info { "Testing" + {currentActiveNamespace} }
            }

            this.itemView.findViewById<TextView>(R.id.info_text).text =
                projects[position].metadata.creationTimestamp

            val showIcon: ImageView = itemView.findViewById(R.id.show)
            showIcon.setOnClickListener {
                info { "ImageView clicked!" }
                val intent = Intent(itemView.context, Watch::class.java).putExtra("namespace",  projects[position].metadata.name)
                itemView.context.startActivity(intent)
            }
            }
        }
    }