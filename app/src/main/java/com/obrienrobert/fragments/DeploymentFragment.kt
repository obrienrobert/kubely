package com.obrienrobert.fragments

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.obrienrobert.adapters.DeploymentAdapter
import com.obrienrobert.main.R

class DeploymentFragment : BaseFragment() {

    override fun layoutId() = R.layout.deployment_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arrayList = ArrayList<String>()
        arrayList.add("Deployment 1")
        arrayList.add("Deployment 2")
        arrayList.add("Deployment 3")
        arrayList.add("Deployment 4")

        val viewManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        val viewAdapter: RecyclerView.Adapter<*> = DeploymentAdapter(arrayList)


        view.findViewById<RecyclerView>(R.id.deployment_recycler_view).apply {

            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_cluster_menu_item -> {
                // navigateTo(AddClusterFragment.newInstance())
                return false
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.setTitle(R.string.deployments)
    }

    companion object {
        fun newInstance(): DeploymentFragment {
            return DeploymentFragment()
        }
    }
}