package com.obrienrobert.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.obrienrobert.adapters.ProjectAdapter
import com.obrienrobert.client.ActiveClient
import com.obrienrobert.client.Requests
import com.obrienrobert.main.R
import io.fabric8.openshift.api.model.Project
import me.nikhilchaudhari.asynkio.core.async
import org.jetbrains.anko.AnkoLogger

class ProjectFragment : BaseFragment(), AnkoLogger {

    override fun layoutId() = R.layout.project_fragment

    private lateinit var projectList: List<Project>
    private lateinit var optionsMenu: Menu
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var noData: TextView

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
            projectList = await { Requests(ActiveClient.newInstance()).getAllNamespaces() }

            val recyclerView = view.findViewById<RecyclerView>(R.id.project_recycler_view)
            noData = view.findViewById(R.id.no_data) as TextView

            when {
                projectList.isNullOrEmpty() -> {
                    recyclerView.visibility = View.GONE
                    noData.visibility = View.VISIBLE
                }
                else -> {
                    recyclerView.visibility = View.VISIBLE
                    noData.visibility = View.GONE
                }
            }

            viewManager = LinearLayoutManager(context)
            viewAdapter = ProjectAdapter(projectList)


            recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = viewManager
                adapter = viewAdapter
                scheduleLayoutAnimation()
            }

        }
    }

    companion object {
        fun newInstance(): ProjectFragment {
            return ProjectFragment()
        }
    }
}