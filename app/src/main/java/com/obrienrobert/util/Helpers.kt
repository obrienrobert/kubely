package com.obrienrobert.util

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import com.obrienrobert.main.R


fun createLoader(activity: FragmentActivity) : AlertDialog {
    val loaderBuilder = AlertDialog.Builder(activity)
        .setCancelable(true) // 'false' if you want user to wait
        .setView(R.layout.loading)
    val loader = loaderBuilder.create()
    loader.setTitle(R.string.app_name)
    loader.setIcon(R.drawable.openshift)

    return loader
}

fun showLoader(loader: AlertDialog, message: String) {
    if (!loader.isShowing) {
        loader.setTitle(message)
        loader.show()
    }
}

fun hideLoader(loader: AlertDialog) {
    if (loader.isShowing)
        loader.dismiss()
}