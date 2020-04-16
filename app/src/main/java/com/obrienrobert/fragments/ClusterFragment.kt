package com.obrienrobert.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.obrienrobert.main.R
import com.obrienrobert.main.SharedViewModel
import com.obrienrobert.models.ClusterModel
import com.obrienrobert.models.ClusterStore
import com.obrienrobert.util.SwipeToDeleteCallback
import com.obrienrobert.util.SwipeToEditCallback
import kotlinx.android.synthetic.main.cluster_card_view.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class ClusterFragment : BaseFragment(), AnkoLogger, View.OnClickListener {

    override fun layoutId() = R.layout.cluster_fragment
    private lateinit var viewModel: SharedViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var noData: TextView

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.add_cluster_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)

        viewModel = activity?.run {
            ViewModelProviders.of(this).get(SharedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_cluster_menu_item -> {
                navigateTo(AddClusterFragment.newInstance())
                return false
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.cluster_recycler_view)
        noData = view.findViewById(R.id.no_data) as TextView

        val query: Query = app.database.child("user-clusters").child(app.auth.currentUser!!.uid)
        val options: FirebaseRecyclerOptions<ClusterModel?> =
            FirebaseRecyclerOptions.Builder<ClusterModel>()
                .setQuery(query, ClusterModel::class.java).build()

        val adapter: FirebaseRecyclerAdapter<*, *> =
            object : FirebaseRecyclerAdapter<ClusterModel?, ViewHolder?>(options) {
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                    return ViewHolder(
                        LayoutInflater.from(parent.context)
                            .inflate(R.layout.cluster_card_view, parent, false)
                    )
                }

                override fun onBindViewHolder(
                    holder: ViewHolder,
                    position: Int,
                    model: ClusterModel
                ) {
                    holder.bind(model)
                }
            }

        val viewManager: RecyclerView.LayoutManager = LinearLayoutManager(context)

        var clustersExist: Boolean
        app.database.child("user-clusters").child(app.auth.currentUser!!.uid).addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    clustersExist = snapshot.exists()
                    when {
                        clustersExist -> {

                            val children = snapshot.children
                            children.forEach {
                                val dataSnapshot = it.getValue(ClusterModel::class.java)
                                if(dataSnapshot?.isActiveCluster!!){

                                    // Load the current active cluster when the app starts
                                    ClusterStore.clusterUid = dataSnapshot.uid as String
                                    ClusterStore.apiURL = dataSnapshot.masterURL as String
                                    ClusterStore.username = dataSnapshot.userName as String
                                    ClusterStore.password = dataSnapshot.password as String
                                    ClusterStore.currentActiveNamespace = dataSnapshot.activeNamespace

                                    info { "Test: $dataSnapshot" }
                                }
                            }

                            recyclerView.layoutManager = viewManager
                            recyclerView.setHasFixedSize(true)
                            recyclerView.adapter = adapter
                            recyclerView.visibility = View.VISIBLE
                            noData.visibility = View.GONE
                        }
                        else -> {
                            recyclerView.visibility = View.GONE
                            noData.visibility = View.VISIBLE
                            noData.setText(R.string.no_cluster)
                        }
                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    info("Firebase Donation error : ${error.message}")
                }
            })

        adapter.startListening()


        val deleteSwipeHandler = object : SwipeToDeleteCallback(this.context!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                app.database.child("user-clusters").child(app.auth.currentUser!!.uid)
                    .child(viewHolder.itemView.firebase_uid.text.toString()).removeValue()
            }
        }

        val itemTouchDeleteHelper = ItemTouchHelper(deleteSwipeHandler)
        itemTouchDeleteHelper.attachToRecyclerView(recyclerView)


        val editSwipeHandler = object : SwipeToEditCallback(this.context!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.data.value = viewHolder.itemView.firebase_uid.text.toString()
                navigateTo(EditClusterFragment.newInstance())
            }
        }

        val itemTouchEditHelper = ItemTouchHelper(editSwipeHandler)
        itemTouchEditHelper.attachToRecyclerView(recyclerView)
    }

    override fun onClick(v: View?) {
    }

    private fun navigateTo(fragment: Fragment) {
        fragmentManager?.apply {
            beginTransaction()
                .replace(R.id.homeFragment, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    companion object {
        fun newInstance(): ClusterFragment {
            return ClusterFragment()
        }
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), AnkoLogger {


        fun bind(cluster: ClusterModel) {
            itemView.cluster_name.text = cluster.uid

            // Setting the uid as a text view so that the swipe gestures work
            itemView.firebase_uid.text = cluster.uid

            itemView.findViewById<ImageView>(R.id.cluster_icon)
                .setImageResource(R.drawable.cluster_icon)

            if (cluster.isActiveCluster) {
                itemView.setBackgroundColor(Color.GREEN)
            } else {
                itemView.setBackgroundColor(Color.BLACK)
            }

            this.itemView.setOnClickListener {
                app.database.child("user-clusters").child(app.auth.currentUser!!.uid)
                    .child(cluster.uid.toString()).child("isActiveCluster").setValue(true)
                itemView.setBackgroundColor(Color.GREEN)

                // Sets the cluster credentials
                ClusterStore.clusterUid = cluster.uid.toString() // We need the UID in order to set the active namespace for the correct cluster
                ClusterStore.apiURL = cluster.masterURL.toString()
                ClusterStore.username = cluster.userName.toString()
                ClusterStore.password = cluster.password.toString()

                app.database.child("user-clusters").child(app.auth.currentUser!!.uid)
                    .addListenerForSingleValueEvent(
                        object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                val children = snapshot.children
                                children.forEach {
                                    val dataSnapshot = it.getValue(ClusterModel::class.java)
                                    if (dataSnapshot?.uid!! != cluster.uid) {
                                        app.database.child("user-clusters")
                                            .child(app.auth.currentUser!!.uid)
                                            .child(dataSnapshot.uid as String)
                                            .child("isActiveCluster").setValue(false)
                                    }
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                info("Firebase Donation error : ${error.message}")
                            }
                        })

            }


        }
    }
}
