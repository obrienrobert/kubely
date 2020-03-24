package com.obrienrobert.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.obrienrobert.adapters.ClusterAdapter
import com.obrienrobert.main.R
import com.obrienrobert.main.SharedViewModel
import com.obrienrobert.models.ClusterStore
import com.obrienrobert.util.SwipeToDeleteCallback
import com.obrienrobert.util.SwipeToEditCallback
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class ClusterFragment : BaseFragment(), AnkoLogger, View.OnClickListener {

    override fun layoutId() = R.layout.cluster_fragment
    private lateinit var viewModel: SharedViewModel

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.add_cluster_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

        viewModel = activity?.run {
            ViewModelProviders.of(this).get(SharedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
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

        val viewManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        val viewAdapter: RecyclerView.Adapter<*> = ClusterAdapter(ClusterStore.listOfClusters)

        val recycleView = view.findViewById<RecyclerView>(R.id.cluster_recycler_view).apply {

            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        val deleteSwipeHandler = object : SwipeToDeleteCallback(this.context!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = recycleView.adapter as ClusterAdapter
                adapter.removeAt(viewHolder.adapterPosition)
            }
        }

        val itemTouchDeleteHelper = ItemTouchHelper(deleteSwipeHandler)
        itemTouchDeleteHelper.attachToRecyclerView(recycleView)


        val editSwipeHandler = object : SwipeToEditCallback(this.context!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.data.value =
                    ClusterStore.listOfClusters[viewHolder.adapterPosition].clusterName
                navigateTo(EditClusterFragment.newInstance())
            }
        }

        val itemTouchEditHelper = ItemTouchHelper(editSwipeHandler)
        itemTouchEditHelper.attachToRecyclerView(recycleView)
    }

    override fun onClick(v: View?) {
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
        fun newInstance(): ClusterFragment {
            return ClusterFragment()
        }
    }
}
