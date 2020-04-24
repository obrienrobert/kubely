package com.obrienrobert.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.materialdialogs.MaterialDialog
import com.obrienrobert.adapters.ProjectAdapter
import com.obrienrobert.client.ActiveClient
import com.obrienrobert.client.Requests
import com.obrienrobert.main.R
import com.obrienrobert.main.Shifty
import com.obrienrobert.models.ClusterStore
import com.obrienrobert.util.SwipeToDeleteCallback
import io.fabric8.openshift.api.model.Project
import kotlinx.android.synthetic.main.project_card_view.view.*
import kotlinx.android.synthetic.main.project_fragment.view.*
import me.nikhilchaudhari.asynkio.core.async
import org.jetbrains.anko.AnkoLogger
import java.util.*


class ProjectFragment : BaseFragment(), AnkoLogger {

    override fun layoutId() = R.layout.project_fragment

    private lateinit var projectList: MutableList<Project>
    private lateinit var optionsMenu: Menu
    private lateinit var recyclerView: RecyclerView
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
                navigateTo(AddProjectFragment.newInstance())
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.project_recycler_view

        async {
            projectList = await { Requests(ActiveClient.newInstance()).getAllNamespaces() }

            noData = view.no_data as TextView

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

            var indexOfCurrentNamespace = 0
            projectList.forEachIndexed { index, element ->
                if (element.metadata.name == ClusterStore.currentActiveNamespace) {
                    indexOfCurrentNamespace = index
                }
            }
            Collections.swap(projectList, 0, indexOfCurrentNamespace)

            viewManager = LinearLayoutManager(context)
            viewAdapter = ProjectAdapter(
                projectList, activity?.application as Shifty

            )

            recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = viewManager
                adapter = viewAdapter
                scheduleLayoutAnimation()
            }

        }

        val deleteSwipeHandler = object : SwipeToDeleteCallback(context!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                MaterialDialog(context!!).show {
                    title(text = "Delete Project")
                    message(text = "Are you sure you want to delete the project " + viewHolder.itemView.resource_name.text.toString() + "?")
                    positiveButton(R.string.yes)
                    negativeButton(R.string.no)
                    icon(R.drawable.warning)

                    positiveButton(R.string.yes) {
                        async {
                            await {
                                Requests(ActiveClient.newInstance()).deleteSpecificNamespace(
                                    viewHolder.itemView.resource_name.text.toString()
                                )
                            }
                        }
                        navigateTo(ClusterFragment.newInstance())
                    }
                    negativeButton(R.string.no) {
                        dismiss()
                        viewAdapter.notifyDataSetChanged()
                    }
                }
            }
        }

        val itemTouchDeleteHelper = ItemTouchHelper(deleteSwipeHandler)
        itemTouchDeleteHelper.attachToRecyclerView(recyclerView)
    }

    private fun navigateTo(fragment: Fragment) {
        fragmentManager?.apply {
            beginTransaction()
                .replace(R.id.homeFragment, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.setTitle(R.string.projects)
    }


    companion object {
        fun newInstance(): ProjectFragment {
            return ProjectFragment()
        }
    }
}