package com.obrienrobert.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.obrienrobert.adapters.RouteAdapter
import com.obrienrobert.main.R
import kotlinx.android.synthetic.main.route_fragment.view.*

class RouteFragment : BaseFragment() {

    override fun layoutId() = R.layout.route_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arrayList = ArrayList<String>()
        arrayList.add("Route 1")
        arrayList.add("Route 2")
        arrayList.add("Route 3")
        arrayList.add("Route 4")

        val viewManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        val viewAdapter: RecyclerView.Adapter<*> = RouteAdapter(arrayList)

        view.route_recycler_view.apply {

            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.setTitle(R.string.routes)
    }

    companion object {
        fun newInstance(): RouteFragment {
            return RouteFragment()
        }
    }
}