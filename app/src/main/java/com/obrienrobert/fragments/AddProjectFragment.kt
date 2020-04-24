package com.obrienrobert.fragments

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.afollestad.vvalidator.field.FieldValue
import com.afollestad.vvalidator.form
import com.afollestad.vvalidator.form.FormResult
import com.obrienrobert.client.ActiveClient
import com.obrienrobert.client.Requests
import com.obrienrobert.main.R
import io.fabric8.openshift.api.model.ProjectRequest
import kotlinx.android.synthetic.main.add_project.view.*
import me.nikhilchaudhari.asynkio.core.async
import org.jetbrains.anko.AnkoLogger


class AddProjectFragment : BaseFragment(), AnkoLogger {

    override fun layoutId() = R.layout.add_project

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.add_project_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_project_menu_item -> {
                validateNewProject()
                return false
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun validateNewProject() {
        val addProjectForm = form {
            input(R.id.project_name) {
                isNotEmpty()
                length().greaterThan(3)
            }
        }.validate()

        if (addProjectForm.success()) {
            val result: FormResult = addProjectForm
            val projectName: FieldValue<*>? = result["project_name"]
            val newProject = projectName?.asString()

            async {
                var request: ProjectRequest? = null
                try {
                    request =
                        await { Requests(ActiveClient.newInstance()).createNamespace(newProject) }
                } finally {
                    if (request != null) {
                        Requests(ActiveClient.newInstance()).deleteSpecificNamespace(newProject)
                    }
                }
            }

            navigateTo(ProjectFragment.newInstance())
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
        view?.project_name?.text?.clear()
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity?)!!.supportActionBar!!.setTitle(R.string.add_project)
    }

    companion object {
        fun newInstance(): AddProjectFragment {
            return AddProjectFragment()
        }
    }
}
