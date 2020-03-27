package com.obrienrobert.fragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
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

            if (nodeList.isEmpty()) {
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
            }
        }
    }

    companion object {
        fun newInstance(): NodeFragment {
            return NodeFragment()
        }
    }
}