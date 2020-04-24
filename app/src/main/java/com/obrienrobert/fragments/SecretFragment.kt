package com.obrienrobert.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.obrienrobert.adapters.SecretAdapter
import com.obrienrobert.main.R
import kotlinx.android.synthetic.main.secret_fragment.view.*

class SecretFragment : BaseFragment() {

    override fun layoutId() = R.layout.secret_fragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arrayList = ArrayList<String>()
        arrayList.add("Secret 1")
        arrayList.add("Secret 2")
        arrayList.add("Secret 3")
        arrayList.add("Secret 4")

        val viewManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        val viewAdapter: RecyclerView.Adapter<*> = SecretAdapter(arrayList)

        view.secret_recycler_view.apply {

            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.setTitle(R.string.secrets)
    }


    companion object {
        fun newInstance(): SecretFragment {
            return SecretFragment()
        }
    }
}