package com.obrienrobert.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.obrienrobert.main.Shifty
import com.obrienrobert.util.createLoader

abstract class BaseFragment : Fragment() {

    @LayoutRes
    protected abstract fun layoutId(): Int

    lateinit var app: Shifty
    lateinit var loader: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        app = activity?.application as Shifty
        loader = createLoader(activity!!)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId(), container, false)
    }
}