package com.obrienrobert.fragments

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.obrienrobert.adapters.PVCAdapter
import com.obrienrobert.main.R

class PVCFragment : BaseFragment() {

    override fun layoutId() = R.layout.pvc_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arrayList = ArrayList<String>()
        arrayList.add("PVC 1")
        arrayList.add("PVC 2")
        arrayList.add("PVC 3")
        arrayList.add("PVC 4")

        val viewManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        val viewAdapter: RecyclerView.Adapter<*> = PVCAdapter(arrayList)

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