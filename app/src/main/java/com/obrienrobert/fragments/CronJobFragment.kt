package com.obrienrobert.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.obrienrobert.adapter.CronJobAdapter
import com.obrienrobert.kubely.R

class CronJobFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.cronjob_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arraylist=ArrayList<String>()
        arraylist.add("Cron Jobs")

        viewManager = LinearLayoutManager(this.context)
        viewAdapter = CronJobAdapter(arraylist)

        recyclerView = view.findViewById<RecyclerView>(R.id.cronjob_recycler_view).apply {
            setHasFixedSize(true)

            layoutManager = viewManager

            adapter = viewAdapter

        }
    }

    companion object {
        fun newInstance(): CronJobFragment {
            return CronJobFragment()
        }
    }
}