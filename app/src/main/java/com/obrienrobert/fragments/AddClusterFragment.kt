package com.obrienrobert.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.afollestad.vvalidator.field.FieldValue
import com.afollestad.vvalidator.form
import com.afollestad.vvalidator.form.FormResult
import com.obrienrobert.main.R
import com.obrienrobert.models.ClusterModel
import com.obrienrobert.models.ClusterStore
import org.jetbrains.anko.AnkoLogger

class AddClusterFragment : BaseFragment(), AnkoLogger {

    override fun layoutId() = R.layout.add_cluster

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
        populateFormFields()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.add_cluster_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_cluster_menu_item -> {
                validateNewCluster()
                return false
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun validateNewCluster() {
        val addClusterForm = form {
            input(R.id.cluster_url) {
                isNotEmpty()
                isUrl()
            }
            input(R.id.cluster_name) {
                isNotEmpty()
                length().greaterThan(6)
                length().lessThan(16)
            }
            input(R.id.username) {
                isNotEmpty()
                length().greaterThan(6)
                length().lessThan(16)
            }
            input(R.id.password) {
                isNotEmpty()
                length().greaterThan(6)
                length().lessThan(20)
            }

        }.validate()

        if (addClusterForm.success()) {
            val result: FormResult = addClusterForm
            val clusterURL: FieldValue<*>? = result["cluster_url"]
            val clusterName: FieldValue<*>? = result["cluster_name"]
            val username: FieldValue<*>? = result["username"]
            val password: FieldValue<*>? = result["password"]

            ClusterStore.listOfClusters.add(
                ClusterModel(
                    clusterURL?.asString(),
                    clusterName?.asString(),
                    username?.asString(),
                    password?.asString()
                )
            )

            navigateTo(ClusterFragment.newInstance())
            clearFormFields()
        }
    }

    private fun navigateTo(fragment: Fragment) {
        fragmentManager?.apply {
            beginTransaction()
                .replace(R.id.homeFragment, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

     private fun populateFormFields() {
        view?.findViewById<EditText>(R.id.cluster_url)?.setHint(R.string.cluster_api_url)
        view?.findViewById<EditText>(R.id.cluster_name)?.setHint(R.string.cluster_name)
        view?.findViewById<EditText>(R.id.username)?.setHint(R.string.user_name)
        view?.findViewById<EditText>(R.id.password)?.setHint(R.string.password)
    }

    private fun clearFormFields() {
        view?.findViewById<EditText>(R.id.cluster_url)?.text?.clear()
        view?.findViewById<EditText>(R.id.cluster_name)?.text?.clear()
        view?.findViewById<EditText>(R.id.username)?.text?.clear()
        view?.findViewById<EditText>(R.id.password)?.text?.clear()
    }

    companion object {
        fun newInstance(): AddClusterFragment {
            return AddClusterFragment()
        }
    }
}
