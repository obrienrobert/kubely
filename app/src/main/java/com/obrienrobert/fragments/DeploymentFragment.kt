package com.obrienrobert.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.obrienrobert.kubely.MainActivity
import com.obrienrobert.kubely.R

class DeploymentFragment : Fragment() {

    lateinit var textView: TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.deployment_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        textView = view.findViewById(R.id.deployment_fragment_text)
        textView.text  = "Deployments"

    }

    companion object {
        fun newInstance(): DeploymentFragment {
            return DeploymentFragment()
        }
    }
}