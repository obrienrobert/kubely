package com.obrienrobert.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.obrienrobert.adapters.PodAdapter
import com.obrienrobert.main.R

class PodFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.pod_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arrayList = ArrayList<String>()
        arrayList.add("Pod 1")
        arrayList.add("Pod 2")
        arrayList.add("Pod 3")
        arrayList.add("Pod 4")

        val viewManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        val viewAdapter: RecyclerView.Adapter<*> = PodAdapter(arrayList)

        view.findViewById<RecyclerView>(R.id.pod_recycler_view).apply {

            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    companion object {
        fun newInstance(): PodFragment {
            return PodFragment()
        }
    }
}