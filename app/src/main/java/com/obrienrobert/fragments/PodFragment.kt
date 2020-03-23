package com.obrienrobert.fragments

import android.os.Bundle
import android.view.View
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        async {
            podList =
                await { Requests(ActiveClient.newInstance()).getAllPodsInNamespace(ClusterStore.getActiveCluster()?.activeNamespace) }

            viewManager = LinearLayoutManager(context)
            viewAdapter = PodAdapter(podList)

            view.findViewById<RecyclerView>(R.id.pod_recycler_view).apply {

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