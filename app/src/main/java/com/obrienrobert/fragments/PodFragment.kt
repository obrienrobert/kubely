package com.obrienrobert.fragments

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.obrienrobert.adapters.PodAdapter
import com.obrienrobert.client.ActiveClient
import com.obrienrobert.client.Requests
import com.obrienrobert.main.R
import com.obrienrobert.models.ClusterStore
import io.fabric8.kubernetes.api.model.Pod
import me.nikhilchaudhari.asynkio.core.async
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info


class PodFragment : BaseFragment(), AnkoLogger {

    override fun layoutId() = R.layout.pod_fragment

    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var optionsMenu: Menu
    private lateinit var podList: List<Pod>
    private lateinit var noData: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        async {
            podList =
                await {
                    Requests(ActiveClient.newInstance()).getAllPodsInNamespace(ClusterStore.currentActiveNamespace)
                }

            val recyclerView = view.findViewById<RecyclerView>(R.id.pod_recycler_view)
            noData = view.findViewById(R.id.no_data) as TextView

            when {
                podList.isNullOrEmpty() -> {
                    recyclerView.visibility = View.GONE
                    noData.visibility = View.VISIBLE
                    noData.setText(R.string.no_data)
                }
                ClusterStore.currentActiveNamespace.isEmpty() -> {
                    recyclerView.visibility = View.GONE
                    noData.visibility = View.VISIBLE
                    noData.setText(R.string.no_project_selected)
                }
                else -> {
                    recyclerView.visibility = View.VISIBLE
                    noData.visibility = View.GONE
                }
            }

            viewManager = LinearLayoutManager(context)
            viewAdapter = PodAdapter(podList)

            recyclerView.apply {
                setHasFixedSize(true)
                layoutManager = viewManager
                adapter = viewAdapter
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.add_resource_menu, menu)
        optionsMenu = menu
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_resource_menu_item -> {

                val intent = Intent()
                    .setType("*/*")
                    .setAction(Intent.ACTION_GET_CONTENT)

                startActivityForResult(Intent.createChooser(intent, "Select a file"), 111)

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 111 && resultCode == AppCompatActivity.RESULT_OK) {
            val selectedFile = data?.data
            info { "Test: $selectedFile" }
        }
    }

    companion object {
        fun newInstance(): PodFragment {
            return PodFragment()
        }
    }
}