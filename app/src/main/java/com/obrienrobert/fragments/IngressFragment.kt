package com.obrienrobert.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.obrienrobert.adapter.IngressAdapter
import com.obrienrobert.kubely.R

class IngressFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.ingress_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arraylist=ArrayList<String>()
        arraylist.add("Ingresses")

        viewManager = LinearLayoutManager(this.context)
        viewAdapter = IngressAdapter(arraylist)

        recyclerView = view.findViewById<RecyclerView>(R.id.ingress_recycler_view).apply {

            setHasFixedSize(true)

            layoutManager = viewManager

            adapter = viewAdapter

        }
    }

    companion object {
        fun newInstance(): IngressFragment {
            return IngressFragment()
        }
    }
}