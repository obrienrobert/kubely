package com.obrienrobert.fragments

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.obrienrobert.adapters.ProjectAdapter
import com.obrienrobert.client.ActiveClient
import com.obrienrobert.client.Requests
import com.obrienrobert.main.R
import io.fabric8.openshift.api.model.Project
import me.nikhilchaudhari.asynkio.core.async
import org.jetbrains.anko.AnkoLogger

class ProjectFragment : Fragment(), AnkoLogger {

    private lateinit var projectList: List<Project>
    private lateinit var optionsMenu: Menu

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.project_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.project_menu, menu)
        optionsMenu = menu
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.project_menu_item -> {
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        async {
            projectList = await { Requests(ActiveClient.client).getAllNamespaces() }

            val viewManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
            val viewAdapter: RecyclerView.Adapter<*> = ProjectAdapter(projectList)

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