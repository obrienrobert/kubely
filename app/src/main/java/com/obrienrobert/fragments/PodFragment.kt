package com.obrienrobert.fragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.obrienrobert.adapters.PodAdapter
import com.obrienrobert.client.ActiveClient
import com.obrienrobert.client.Requests
import com.obrienrobert.main.R
import com.obrienrobert.models.ClusterStore
import io.fabric8.kubernetes.api.model.Pod
import me.nikhilchaudhari.asynkio.core.async
import org.jetbrains.anko.AnkoLogger

class PodFragment : BaseFragment(), AnkoLogger {

    override fun layoutId() = R.layout.pod_fragment

    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var podList: List<Pod>
    private lateinit var noData: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        async {
            podList =
                await { Requests(ActiveClient.newInstance()).getAllPodsInNamespace(ClusterStore.getActiveCluster()?.activeNamespace)
                }

            val recyclerView = view.findViewById<RecyclerView>(R.id.pod_recycler_view)
            noData = view.findViewById(R.id.no_data) as TextView

            when {
                podList.isNullOrEmpty() -> {
                    recyclerView.visibility = View.GONE
                    noData.visibility = View.VISIBLE
                    noData.setText(R.string.no_data)
                }
                ClusterStore.getActiveCluster()?.activeNamespace.isNullOrEmpty() -> {
                    recyclerView.visibility = View.GONE
                    noData.visibility = View.VISIBLE
                    noData.setText(R.string.no_project_selected)
                }
                else -> {
                    recyclerView.visibility = View.VISIBLE
                    noData.visibility = View.GONE
                }
            }

            viewManager = LinearLayoutManager(context)
            viewAdapter = PodAdapter(podList)

           recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = viewManager
                adapter = viewAdapter
            }
        }
    }

    companion object {
        fun newInstance(): PodFragment {
            return PodFragment()
        }
    }
}