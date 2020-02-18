package com.obrienrobert.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.obrienrobert.adapter.ClusterAdapter
import com.obrienrobert.client.Client
import com.obrienrobert.kubely.R
import io.fabric8.kubernetes.api.model.Pod
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.jetbrains.anko.AnkoLogger

class ClusterFragment : Fragment(), AnkoLogger {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var arr: List<Pod>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.cluster_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val client = Client(
            "<MASTER_URL>",
            "<OAUTH_TOKEN>"
        )
        // Cannot execute network calls on the main thread
        GlobalScope.async {
            arr = client.getAllPodsInNamespace("openshift-apiserver-operator")

            // arr.forEachIndexed { index, element -> info("Spec: $element") }
        }

        Thread.sleep(10000)

        viewManager = LinearLayoutManager(this.context)
        viewAdapter = ClusterAdapter(arr)

        recyclerView = view.findViewById<RecyclerView>(R.id.my_recycler_view).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }
    }

    companion object {
        fun newInstance(): ClusterFragment {
            return ClusterFragment()
        }
    }


}
