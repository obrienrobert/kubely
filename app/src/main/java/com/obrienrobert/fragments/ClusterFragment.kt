package com.obrienrobert.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.obrienrobert.main.R
import com.obrienrobert.main.SharedViewModel
import com.obrienrobert.models.ClusterModel
import kotlinx.android.synthetic.main.cluster_fragment.view.*
import org.jetbrains.anko.AnkoLogger


class ClusterFragment : BaseFragment(), AnkoLogger, View.OnClickListener {

    override fun layoutId() = R.layout.cluster_fragment
    private lateinit var viewModel: SharedViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var noData: TextView

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
                navigateTo(AddClusterFragment.newInstance())
                return false
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.cluster_recycler_view
        noData = view.no_data as TextView


        val viewManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = viewManager

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

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.setTitle(R.string.clusters)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), AnkoLogger {

        fun bind(cluster: ClusterModel) {

            if (cluster.isActiveCluster) {
                itemView.setBackgroundColor(Color.GREEN)
            } else {
                itemView.setBackgroundColor(Color.BLACK)
            }
        }
    }
}
