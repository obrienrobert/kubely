package com.obrienrobert.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nicolettilu.hiddensearchwithrecyclerview.HiddenSearchWithRecyclerView
import com.nicolettilu.scrolldowntosearchrecyclerview.utils.hide
import com.obrienrobert.adapters.ClusterAdapter
import com.obrienrobert.main.R
import kotlinx.android.synthetic.main.cluster_fragment.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info


class ClusterFragment : Fragment(), AnkoLogger {

    private lateinit var emptyView: TextView
    private lateinit var addClusterButton: Button
    private lateinit var hiddenSearchWithRecyclerView: HiddenSearchWithRecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.cluster_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arrayList = ArrayList<String>()

        val viewManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        val viewAdapter: RecyclerView.Adapter<*> = ClusterAdapter(arrayList)

        emptyView = view.findViewById(R.id.empty_list)
        hiddenSearchWithRecyclerView = view.findViewById(R.id.hiddenSearch)
        addClusterButton = view.findViewById(R.id.add_cluster)

        val button = view.findViewById(R.id.add_cluster) as Button
        button.setOnClickListener {
            info { "Add cluster button clicked" }
        }

        if (arrayList.isEmpty()) {
            empty_list.visibility = View.VISIBLE
            view.findViewById<RecyclerView>(R.id.cluster_recycler_view).visibility = View.GONE
            hiddenSearchWithRecyclerView.hide()
        } else {
            view.findViewById<RecyclerView>(R.id.cluster_recycler_view).apply {
                setHasFixedSize(true)
                layoutManager = viewManager
                adapter = viewAdapter
                empty_list.visibility = View.GONE
                addClusterButton.visibility = View.INVISIBLE

            }
        }
    }

    companion object {
        fun newInstance(): ClusterFragment {
            return ClusterFragment()
        }
    }
}
