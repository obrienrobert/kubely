package com.obrienrobert.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.obrienrobert.adapters.NodeAdapter
import com.obrienrobert.client.ActiveClient
import com.obrienrobert.client.Requests
import com.obrienrobert.main.R
import io.fabric8.kubernetes.api.model.Node
import me.nikhilchaudhari.asynkio.core.async

class NodeFragment : BaseFragment() {

    override fun layoutId() = R.layout.node_fragment

    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var nodeList: List<Node>
    private lateinit var noData: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        async {
            nodeList = await { Requests(ActiveClient.newInstance()).getAllNodes() }

            val recyclerView = view.findViewById<RecyclerView>(R.id.node_recycler_view)
            noData = view.findViewById(R.id.no_data) as TextView

            if (nodeList.isNullOrEmpty()) {
                recyclerView.visibility = View.GONE
                noData.visibility = View.VISIBLE
            } else {
                recyclerView.visibility = View.VISIBLE
                noData.visibility = View.GONE
            }

            viewManager = LinearLayoutManager(context)
            viewAdapter = NodeAdapter(nodeList)

            recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = viewManager
                adapter = viewAdapter
                scheduleLayoutAnimation()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // No menu action allow from here, so we just clear the action bar
        menu.clear()
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.setTitle(R.string.nodes)
    }


    companion object {
        fun newInstance(): NodeFragment {
            return NodeFragment()
        }
    }
}