package com.obrienrobert.fragments

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.afollestad.vvalidator.field.FieldValue
import com.afollestad.vvalidator.form
import com.afollestad.vvalidator.form.FormResult
import com.obrienrobert.main.R
import com.obrienrobert.models.ClusterModel
import com.obrienrobert.util.hideLoader
import com.obrienrobert.util.showLoader
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.util.*

class AddClusterFragment : BaseFragment(), AnkoLogger {

    override fun layoutId() = R.layout.add_cluster

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
                //isNotEmpty()
                //isUrl()
            }
            input(R.id.cluster_name) {
                //isNotEmpty()
                //length().greaterThan(6)
                //length().lessThan(16)
            }
            input(R.id.username) {
                //isNotEmpty()
                //length().greaterThan(6)
                //length().lessThan(16)
            }
            input(R.id.password) {
                //isNotEmpty()
                //length().greaterThan(6)
                //length().lessThan(20)
            }

        }.validate()

        if (addClusterForm.success()) {
            val result: FormResult = addClusterForm
            val clusterURL: FieldValue<*>? = result["cluster_url"]
            val clusterName: FieldValue<*>? = result["cluster_name"]
            val username: FieldValue<*>? = result["username"]
            val password: FieldValue<*>? = result["password"]

            showLoader(loader, "Adding cluster")

            val uid = app.auth.currentUser!!.uid
            val key = app.database.child("user-clusters").push().key

            if (key == null) {
                info("Firebase Error : Key Empty")
                return
            }
            val newCluster = ClusterModel(
                key,
                clusterURL?.asString(),
                clusterName?.asString(),
                username?.asString(),
                password?.asString()
            ).toMap()

            val childUpdates = HashMap<String, Any>()
            childUpdates["/user-clusters/$uid/$key"] = newCluster

            app.database.updateChildren(childUpdates)
            hideLoader(loader)

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

    private fun clearFormFields() {
        view?.findViewById<EditText>(R.id.cluster_url)?.text?.clear()
        view?.findViewById<EditText>(R.id.cluster_name)?.text?.clear()
        view?.findViewById<EditText>(R.id.username)?.text?.clear()
        view?.findViewById<EditText>(R.id.password)?.text?.clear()
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.setTitle(R.string.add_cluster)
    }

    companion object {
        fun newInstance(): AddClusterFragment {
            return AddClusterFragment()
        }
    }
}
