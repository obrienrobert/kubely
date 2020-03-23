package com.obrienrobert.fragments

import android.os.Bundle
import android.view.View
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        async {
            nodeList = await { Requests(ActiveClient.newInstance()).getAllNodes() }

            viewManager = LinearLayoutManager(context)
            viewAdapter = NodeAdapter(nodeList)

            view.findViewById<RecyclerView>(R.id.node_recycler_view).apply {

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