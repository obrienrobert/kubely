package com.obrienrobert.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.*
import com.ch4vi.flowlayoutmanager.FlowLayoutManager
import com.obrienrobert.adapters.DashboardAdapter
import com.obrienrobert.main.R
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class DashboardFragment : BaseFragment(), AnkoLogger {

    override fun layoutId() = R.layout.dashboard_fragment

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.add_cluster_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_cluster_menu_item -> {
                info { "Cluster menu button clicked!" }
                navigateTo(AddClusterFragment.newInstance())
                return false
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewAdapter: Adapter<*> = DashboardAdapter()

        val recycleView = view.findViewById<RecyclerView>(R.id.dashboard_recycler_view)

        val manager = FlowLayoutManager(2, VERTICAL, object : FlowLayoutManager.Interface {
            override fun getProportionalSizeForChild(position: Int): Pair<Int, Int> {
                return when (position) {
                    0 -> Pair(2, 1)
                    1 -> Pair(2, 1)
                    2 -> Pair(1, 1)
                    else -> Pair(1, 1)
                }
            }
        })

        recycleView.apply {
            layoutManager = manager
            adapter = viewAdapter
            addItemDecoration(manager.InsetDecoration(activity?.applicationContext!!))

        }
    }

    private fun navigateTo(fragment: Fragment) {
        fragmentManager?.apply {
            beginTransaction()
                .replace(R.id.homeFragment, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    companion object {
        fun newInstance(): DashboardFragment {
            return DashboardFragment()
        }
    }
}
