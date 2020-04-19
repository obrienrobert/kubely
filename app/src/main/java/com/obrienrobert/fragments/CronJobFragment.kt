package com.obrienrobert.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.obrienrobert.adapters.CronJobAdapter
import com.obrienrobert.main.R

class CronJobFragment : BaseFragment() {

    override fun layoutId() = R.layout.cronjob_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arrayList = ArrayList<String>()
        arrayList.add("CronJob 1")
        arrayList.add("CronJob 2")
        arrayList.add("CronJob 3")
        arrayList.add("CronJob 4")

        val viewManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        val viewAdapter: RecyclerView.Adapter<*> = CronJobAdapter(arrayList)

        view.findViewById<RecyclerView>(R.id.cronjob_recycler_view).apply {

            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.setTitle(R.string.cron_jobs)
    }

    companion object {
        fun newInstance(): CronJobFragment {
            return CronJobFragment()
        }
    }
}