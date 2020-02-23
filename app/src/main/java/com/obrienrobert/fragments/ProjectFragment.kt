package com.obrienrobert.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.obrienrobert.adapters.ProjectAdapter
import com.obrienrobert.client.Client
import com.obrienrobert.client.Requests
import com.obrienrobert.main.R
import io.fabric8.openshift.api.model.Project
import me.nikhilchaudhari.asynkio.core.async

class ProjectFragment : Fragment() {

    private lateinit var projectList: List<Project>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.project_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arrayList=ArrayList<String>()
        arrayList.add("Clusters")
        arrayList.add("Pods")
        arrayList.add("Nodes")
        arrayList.add("Storage")

        val client = Client(
            "<MASTER_URL>",
            "<OAUTH_TOKEN>"
        ).getClient()

        async {
            projectList = await { Requests(client).getAllNamespaces() }

            val viewManager:  RecyclerView.LayoutManager = LinearLayoutManager(context)
            val viewAdapter: RecyclerView.Adapter<*> =  ProjectAdapter(projectList)

            view.findViewById<RecyclerView>(R.id.project_recycler_view).apply {

                setHasFixedSize(true)
                layoutManager = viewManager
                adapter = viewAdapter
            }
        }
    }

    companion object {
        fun newInstance(): ProjectFragment {
            return ProjectFragment()
        }
    }
}