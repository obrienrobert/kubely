package com.obrienrobert.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import co.zsmb.materialdrawerkt.builders.drawer
import co.zsmb.materialdrawerkt.draweritems.badgeable.primaryItem
import co.zsmb.materialdrawerkt.draweritems.sectionHeader
import com.obrienrobert.fragments.*
import com.obrienrobert.models.ClusterModel
import com.obrienrobert.models.ClusterStore
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger


class Main : AppCompatActivity(), AnkoLogger {

    lateinit var app: Shifty

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        app = application as Shifty

        ClusterStore.listOfClusters.add(
            ClusterModel(
                "<MASTER_URL>",
                "<API_URL>",
                "Test cluster",
                "kubeadmin",
                "<PASSWORD>",
                true
            )
        )

        navigateTo(ClusterFragment.newInstance())
        createNavDrawer()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.main_menu_item -> {
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun createNavDrawer() {
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolBarResource)
        supportActionBar?.title = R.string.clusters.toString()


        // Nav draw setup
        drawer {
            sectionHeader("Home").divider = false
            primaryItem("Clusters") {
                icon = R.drawable.openshift
                onClick { _ ->
                    navigateTo(ClusterFragment.newInstance())
                    false
                }
            }
            primaryItem("Projects") {
                icon = R.drawable.project
                onClick { _ ->
                    navigateTo(ProjectFragment.newInstance())
                    false
                }
            }
            primaryItem("Events") {
                icon = R.drawable.events
                onClick { _ ->
                    navigateTo(EventFragment.newInstance())
                    false
                }
            }
            sectionHeader("Workloads")
            primaryItem("Pods") {
                icon = R.drawable.pod
                onClick { _ ->
                    navigateTo(PodFragment.newInstance())
                    false
                }

            }
            primaryItem("Deployments") {
                icon = R.drawable.deployment
                onClick { _ ->
                    navigateTo(DeploymentFragment.newInstance())
                    false
                }

            }
            primaryItem("Secrets") {
                icon = R.drawable.secret
                onClick { _ ->
                    navigateTo(SecretFragment.newInstance())
                    false
                }

            }
            primaryItem("Cron Jobs") {
                icon = R.drawable.cron_job
                onClick { _ ->
                    navigateTo(CronJobFragment.newInstance())
                    false
                }

            }
            sectionHeader("Networking")
            primaryItem("Services") {
                icon = R.drawable.service
                onClick { _ ->
                    navigateTo(ServiceFragment.newInstance())
                    false
                }

            }
            primaryItem("Routes") {
                icon = R.drawable.route
                onClick { _ ->
                    navigateTo(RouteFragment.newInstance())
                    false
                }

            }
            sectionHeader("Storage")
            primaryItem("Persistent Volumes") {
                icon = R.drawable.pv
                onClick { _ ->
                    navigateTo(PVFragment.newInstance())
                    false
                }

            }
            primaryItem("Persistent Volume Claims") {
                icon = R.drawable.pvc
                onClick { _ ->
                    navigateTo(PVCFragment.newInstance())
                    false
                }
            }
            sectionHeader("Compute")
            primaryItem("Nodes") {
                icon = R.drawable.node
                onClick { _ ->
                    navigateTo(NodeFragment.newInstance())
                    false
                }
            }
        }
    }

    private fun navigateTo(fragment: Fragment) {
        supportFragmentManager.apply {
            beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.homeFragment, fragment)
                .addToBackStack(null)
                .commit()
        }
    }
}