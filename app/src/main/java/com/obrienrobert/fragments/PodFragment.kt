package com.obrienrobert.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.obrienrobert.adapters.PodAdapter
import com.obrienrobert.client.ActiveClient
import com.obrienrobert.client.ActiveNamespace
import com.obrienrobert.client.Client
import com.obrienrobert.client.Requests
import com.obrienrobert.main.R
import io.fabric8.kubernetes.api.model.Pod
import me.nikhilchaudhari.asynkio.core.async

class PodFragment : Fragment() {

    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var podList: List<Pod>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.pod_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        async {
            podList = await { Requests(ActiveClient.client).getAllPodsInNamespace(ActiveNamespace.currentActiveNamespace) }

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