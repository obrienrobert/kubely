package com.obrienrobert.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.obrienrobert.adapters.EventAdapter
import com.obrienrobert.client.ActiveClient
import com.obrienrobert.client.Requests
import io.fabric8.kubernetes.api.model.Event
import kotlinx.android.synthetic.main.activity_main.*
import me.nikhilchaudhari.asynkio.core.async
import org.jetbrains.anko.AnkoLogger


class Watch : AppCompatActivity(), AnkoLogger {

    private lateinit var eventList: List<Event>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watch)
        setSupportActionBar(toolBarResource)
        supportActionBar?.setTitle(R.string.watch_events)

        val namespaceEvents = intent.getStringExtra("namespace")

        async {
            eventList = await { Requests(ActiveClient.newInstance()).getAllEventsInNamespace(namespaceEvents).sortedByDescending { it.lastTimestamp } }

            val viewManager: RecyclerView.LayoutManager = LinearLayoutManager(applicationContext)
            val viewAdapter: RecyclerView.Adapter<*> = EventAdapter(eventList)

            findViewById<RecyclerView>(R.id.watch_resource_recycler_view).apply {
                setHasFixedSize(true)
                layoutManager = viewManager
                adapter = viewAdapter
            }
        }
    }

}
