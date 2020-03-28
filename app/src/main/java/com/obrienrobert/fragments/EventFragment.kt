package com.obrienrobert.fragments

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.obrienrobert.adapters.EventAdapter
import com.obrienrobert.client.ActiveClient
import com.obrienrobert.client.Requests
import com.obrienrobert.main.R
import com.obrienrobert.models.ClusterStore
import io.fabric8.kubernetes.api.model.Event
import me.nikhilchaudhari.asynkio.core.async
import org.jetbrains.anko.AnkoLogger

class EventFragment : BaseFragment(), AnkoLogger {

    override fun layoutId() = R.layout.event_fragment
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var eventList: List<Event>
    private lateinit var noData: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        async {
            eventList = await {
                Requests(ActiveClient.newInstance()).getAllEventsInNamespace(ClusterStore.getActiveCluster()?.activeNamespace!!)
                    .sortedByDescending { it.lastTimestamp }
            }

            val recyclerView = view.findViewById<RecyclerView>(R.id.watch_recycler_view)
            noData = view.findViewById(R.id.no_data) as TextView

            if (eventList.isNullOrEmpty()) {
                recyclerView.visibility = View.GONE
                noData.visibility = View.VISIBLE
            } else {
                recyclerView.visibility = View.VISIBLE
                noData.visibility = View.GONE
            }

            viewManager = LinearLayoutManager(context)
            viewAdapter = EventAdapter(eventList)

            recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = viewManager
                adapter = viewAdapter
            }
        }
    }

    companion object {
        fun newInstance(): EventFragment {
            return EventFragment()
        }
    }
}
