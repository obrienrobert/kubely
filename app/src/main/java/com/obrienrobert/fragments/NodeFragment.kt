package com.obrienrobert.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.obrienrobert.adapters.NodeAdapter
import com.obrienrobert.client.Client
import com.obrienrobert.client.Requests
import com.obrienrobert.main.R
import io.fabric8.kubernetes.api.model.Node
import me.nikhilchaudhari.asynkio.core.async

class NodeFragment : Fragment() {

    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var nodeList: List<Node>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.node_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val client = Client(
            "<MASTER_URL>",
            "<USERNAME>",
            "<PASSWORD>"
        ).getClient()

        async {
            nodeList = await { Requests(client).getAllNodes() }

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