package com.obrienrobert.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.obrienrobert.adapters.ServiceAdapter
import com.obrienrobert.client.ActiveClient
import com.obrienrobert.client.Requests
import com.obrienrobert.main.R
import com.obrienrobert.models.ClusterStore
import io.fabric8.kubernetes.api.model.Service
import me.nikhilchaudhari.asynkio.core.async


class ServiceFragment : BaseFragment() {

    override fun layoutId() = R.layout.service_fragment

    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var serviceList: List<Service>
    private lateinit var emptyView: TextView

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


            val rc = view.findViewById<RecyclerView>(R.id.service_recycler_view)
            emptyView = view.findViewById(R.id.empty_view) as TextView

            if (serviceList.isEmpty()) {
                rc.visibility = View.GONE
                emptyView.visibility = View.VISIBLE
            } else {
                rc.visibility = View.VISIBLE
                emptyView.visibility = View.GONE
            }

            viewManager = LinearLayoutManager(context)
            viewAdapter = ServiceAdapter(serviceList)

            rc.apply {
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