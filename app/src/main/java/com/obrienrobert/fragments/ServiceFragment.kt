package com.obrienrobert.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.obrienrobert.adapters.ServiceAdapter
import com.obrienrobert.client.ActiveClient
import com.obrienrobert.client.Requests
import com.obrienrobert.main.R
import com.obrienrobert.models.ClusterStore
import io.fabric8.kubernetes.api.model.Service
import me.nikhilchaudhari.asynkio.core.async

class ServiceFragment : Fragment() {

    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var serviceList: List<Service>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.service_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        async {
            serviceList = await {
                Requests(ActiveClient.newInstance()).getAllServicesInNamespace(
                    ClusterStore.getActiveCluster()?.activeNamespace!!
                )
            }

            viewManager = LinearLayoutManager(context)
            viewAdapter = ServiceAdapter(serviceList)

            view.findViewById<RecyclerView>(R.id.service_recycler_view).apply {

                setHasFixedSize(true)
                layoutManager = viewManager
                adapter = viewAdapter
            }
        }
    }

    companion object {
        fun newInstance(): ServiceFragment {
            return ServiceFragment()
        }
    }
}