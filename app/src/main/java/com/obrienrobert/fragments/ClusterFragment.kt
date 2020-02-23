package com.obrienrobert.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.obrienrobert.adapters.ClusterAdapter
import com.obrienrobert.client.Client
import com.obrienrobert.main.R
import me.nikhilchaudhari.asynkio.core.async
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import com.obrienrobert.client.Requests
import io.fabric8.kubernetes.api.model.Pod

class ClusterFragment : Fragment(), AnkoLogger {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var podList: List<Pod>

    override fun onCreateView (
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.cluster_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arraylist=ArrayList<String>()
        arraylist.add("Clusters")

        val client = Client(
            "<MASTER_URL>",
            "<OAUTH_TOKEN>"
        ).getClient()

        async {
            podList = await { Requests(client).getAllPodsInNamespace("<NAMESPACE>") }
            info { podList }


            viewManager = LinearLayoutManager(context)
            viewAdapter = ClusterAdapter(podList)

            recyclerView = view.findViewById<RecyclerView>(R.id.cluster_recycler_view).apply {

                setHasFixedSize(true)
                layoutManager = viewManager

                adapter = viewAdapter

            }
        }
    }

    companion object {
        fun newInstance(): ClusterFragment {
            return ClusterFragment()
        }
    }
}
