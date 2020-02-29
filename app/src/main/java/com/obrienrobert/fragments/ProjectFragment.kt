package com.obrienrobert.fragments

import android.os.Bundle
import android.util.Log
import android.view.*
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

    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var projectList: List<Project>

    lateinit var optionsMenu: Menu

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.project_fragment, container, false)
    }

    //enable options menu in this fragment
    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    //inflate the menu
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.project_menu, menu)
        optionsMenu = menu
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.project_menu_item -> {
                Log.d("TEST TEST TEST", "Add Project!")
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arrayList = ArrayList<String>()
        arrayList.add("Project 1")
        arrayList.add("Project 2")
        arrayList.add("Project 3")
        arrayList.add("Project 4")

        val client = Client(
            "<MASTER_URL>",
            "<USERNAME>",
            "<PASSWORD>"
        ).getClient()

        async {
            projectList = await { Requests(client).getAllNamespaces() }

            viewManager = LinearLayoutManager(context)
            viewAdapter = ProjectAdapter(projectList)

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