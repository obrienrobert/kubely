package com.obrienrobert.fragments

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.afollestad.vvalidator.field.FieldValue
import com.afollestad.vvalidator.form
import com.afollestad.vvalidator.form.FormResult
import com.obrienrobert.main.R
import com.obrienrobert.main.SharedViewModel
import com.obrienrobert.models.ClusterModel
import kotlinx.android.synthetic.main.edit_cluster.view.*
import org.jetbrains.anko.AnkoLogger

class EditClusterFragment : BaseFragment(), AnkoLogger {

    override fun layoutId() = R.layout.edit_cluster
    private lateinit var viewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)

        viewModel = activity?.run {
            ViewModelProviders.of(this).get(SharedViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.edit_cluster_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.edit_cluster_menu_item -> {
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
                length().greaterThan(3)
                length().lessThan(20)
            }
            input(R.id.username) {
                isNotEmpty()
                length().greaterThan(3)
                length().lessThan(25)
            }
            input(R.id.password) {
                isNotEmpty()
                length().greaterThan(4)
                length().lessThan(40)
            }

        }.validate()

        if (addClusterForm.success()) {
            val result: FormResult = addClusterForm
            val clusterURL: FieldValue<*>? = result["cluster_url"]
            val clusterName: FieldValue<*>? = result["cluster_name"]
            val username: FieldValue<*>? = result["username"]
            val password: FieldValue<*>? = result["password"]

            val map: HashMap<String, Any?> = hashMapOf(
                "masterURL" to clusterURL?.asString(),
                "clusterName" to clusterName?.asString(),
                "username" to username?.asString(),
                "password" to password?.asString()
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

    private fun populateFormFields(clusterToEdit: ClusterModel?) {
        view?.cluster_url?.setText(clusterToEdit?.masterURL)
        view?.cluster_name?.setText(clusterToEdit?.clusterName)
        view?.username?.setText(clusterToEdit?.userName)
        view?.password?.setText(clusterToEdit?.password)
    }

    private fun clearFormFields() {
        view?.cluster_url?.text?.clear()
        view?.cluster_name?.text?.clear()
        view?.username?.text?.clear()
        view?.password?.text?.clear()
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.setTitle(R.string.edit_cluster)
    }

    companion object {
        fun newInstance(): EditClusterFragment {
            return EditClusterFragment()
        }
    }
}
