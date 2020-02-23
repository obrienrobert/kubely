package com.obrienrobert.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.obrienrobert.adapters.PVCAdapter
import com.obrienrobert.main.R

class PVCFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.pvc_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arrayList=ArrayList<String>()
        arrayList.add("Clusters")
        arrayList.add("Pods")
        arrayList.add("Nodes")
        arrayList.add("Storage")

        val viewManager:  RecyclerView.LayoutManager = LinearLayoutManager(context)
        val viewAdapter: RecyclerView.Adapter<*> =  PVCAdapter(arrayList)

        view.findViewById<RecyclerView>(R.id.pvc_recycler_view).apply {

            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

    }

    companion object {
        fun newInstance(): PVCFragment {
            return PVCFragment()
        }
    }
}