package com.obrienrobert.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.obrienrobert.adapters.EventAdapter
import com.obrienrobert.client.ActiveClient
import com.obrienrobert.client.Requests
import com.obrienrobert.main.R
import com.obrienrobert.models.ClusterStore
import io.fabric8.kubernetes.api.model.Event
import kotlinx.android.synthetic.main.event_fragment.view.*
import me.nikhilchaudhari.asynkio.core.async
import org.jetbrains.anko.AnkoLogger

class EventFragment : BaseFragment(), AnkoLogger {

    override fun layoutId() = R.layout.event_fragment
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var optionsMenu: Menu
    private lateinit var eventList: List<Event>
    private lateinit var noData: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        async {
            eventList = await {
                Requests(ActiveClient.newInstance()).getAllEventsInNamespace(ClusterStore.currentActiveNamespace)
                    .sortedByDescending { it.lastTimestamp }
            }

            val recyclerView = view.watch_recycler_view
            noData = view.no_data as TextView

            when {
                eventList.isNullOrEmpty() -> {
                    recyclerView.visibility = View.GONE
                    noData.visibility = View.VISIBLE
                    noData.setText(R.string.no_data)
                }
                ClusterStore.currentActiveNamespace.isEmpty() -> {
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
            viewAdapter = EventAdapter(eventList)

            recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = viewManager
                adapter = viewAdapter
                scheduleLayoutAnimation()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        optionsMenu = menu
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.setTitle(R.string.namespace_events)
    }


    companion object {
        fun newInstance(): EventFragment {
            return EventFragment()
        }
    }
}
