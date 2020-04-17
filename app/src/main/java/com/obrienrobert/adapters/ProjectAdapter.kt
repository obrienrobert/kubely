package com.obrienrobert.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.obrienrobert.main.R
import com.obrienrobert.main.Shifty
import com.obrienrobert.models.ClusterStore
import io.fabric8.openshift.api.model.Project
import kotlinx.android.synthetic.main.project_card_view.view.*
import org.jetbrains.anko.AnkoLogger


class ProjectAdapter(private val projectList: List<Project>, context: Shifty) :
    RecyclerView.Adapter<ProjectAdapter.ViewHolder>(), Filterable, AnkoLogger {

    var app: Shifty = context

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

            itemView.resource_name.text = projects[position].metadata.name

            itemView.project_icon.setImageResource(R.drawable.project_icon)

            itemView.resource_info.text = projects[position].metadata.creationTimestamp

            if (projects[position].metadata.name == ClusterStore.currentActiveNamespace) {
                itemView.setBackgroundColor(Color.GREEN)
            } else {
                itemView.setBackgroundColor(Color.BLACK)
            }

            itemView.setOnClickListener {
                ClusterStore.currentActiveNamespace = projects[position].metadata.name
                itemView.setBackgroundColor(Color.GREEN)
                notifyDataSetChanged()

                app.database.child("user-clusters")
                    .child(app.auth.currentUser!!.uid)
                    .child(ClusterStore.clusterUid)
                    .child("activeNamespace").setValue(projects[position].metadata.name)
            }
        }
    }
}