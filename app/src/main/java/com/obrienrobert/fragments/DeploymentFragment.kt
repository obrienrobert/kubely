package com.obrienrobert.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.obrienrobert.adapters.DeploymentAdapter
import com.obrienrobert.main.R

class DeploymentFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.deployment_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arrayList = ArrayList<String>()
        arrayList.add("Deployment 1")
        arrayList.add("Deployment 2")
        arrayList.add("Deployment 3")
        arrayList.add("Deployment 4")


        val viewManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        val viewAdapter: RecyclerView.Adapter<*> = DeploymentAdapter(arrayList)

        view.findViewById<RecyclerView>(R.id.deployment_recycler_view).apply {

            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    companion object {
        fun newInstance(): DeploymentFragment {
            return DeploymentFragment()
        }
    }
}