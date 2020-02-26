package com.obrienrobert.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.obrienrobert.adapters.ServiceAdapter
import com.obrienrobert.main.R

class ServiceFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.service_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arrayList = ArrayList<String>()
        arrayList.add("Service 1")
        arrayList.add("Service 2")
        arrayList.add("Service 3")
        arrayList.add("Service 4")

        val viewManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        val viewAdapter: RecyclerView.Adapter<*> = ServiceAdapter(arrayList)

        view.findViewById<RecyclerView>(R.id.service_recycler_view).apply {

            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    companion object {
        fun newInstance(): ServiceFragment {
            return ServiceFragment()
        }
    }
}