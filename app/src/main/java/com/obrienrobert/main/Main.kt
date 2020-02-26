package com.obrienrobert.main

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import co.zsmb.materialdrawerkt.builders.drawer
import co.zsmb.materialdrawerkt.draweritems.badgeable.primaryItem
import co.zsmb.materialdrawerkt.draweritems.sectionHeader
import com.obrienrobert.fragments.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger

class Main : AppCompatActivity(), AnkoLogger {

    private val fragmentManager = supportFragmentManager
    private val clusterFragment = ClusterFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setActionBarTitle(R.string.clusters)

        // Display First Fragment initially
        fragmentManager.beginTransaction().replace(R.id.homeFragment, clusterFragment).commit()

        // Nav draw setup
        drawer {
            sectionHeader("Home")
            primaryItem("Clusters") {
                icon = R.drawable.openshift
                onClick { _ ->
                    Log.d("DRAWER", "Clusters")
                    setActionBarTitle(R.string.clusters)
                    navigateTo(ClusterFragment.newInstance())
                    false
                }
            }
            primaryItem("Projects") {
                icon = R.drawable.project
                onClick { _ ->
                    Log.d("DRAWER", "Projects")
                    setActionBarTitle(R.string.projects)
                    navigateTo(ProjectFragment.newInstance())
                    false
                }
            }
            sectionHeader("Workloads")
            primaryItem("Pods") {
                icon = R.drawable.pod
                onClick { _ ->
                    Log.d("DRAWER", "Pods")
                    setActionBarTitle(R.string.pods)
                    navigateTo(PodFragment.newInstance())
                    false
                }

            }
            primaryItem("Deployments") {
                icon = R.drawable.deployment
                onClick { _ ->
                    Log.d("DRAWER", "Deployments")
                    setActionBarTitle(R.string.deployments)
                    navigateTo(DeploymentFragment.newInstance())
                    false
                }

            }
            primaryItem("Secrets") {
                icon = R.drawable.secret
                onClick { _ ->
                    Log.d("DRAWER", "Secrets")
                    setActionBarTitle(R.string.secrets)
                    navigateTo(SecretFragment.newInstance())
                    false
                }

            }
            primaryItem("Cron Jobs") {
                icon = R.drawable.cron_job
                onClick { _ ->
                    Log.d("DRAWER", "Cron Jobs")
                    setActionBarTitle(R.string.cron_jobs)
                    navigateTo(CronJobFragment.newInstance())
                    false
                }

            }
            sectionHeader("Networking")
            primaryItem("Services") {
                icon = R.drawable.service
                onClick { _ ->
                    Log.d("DRAWER", "Services")
                    setActionBarTitle(R.string.services)
                    navigateTo(ServiceFragment.newInstance())
                    false
                }

            }
            primaryItem("Routes") {
                icon = R.drawable.route
                onClick { _ ->
                    Log.d("DRAWER", "Routes")
                    setActionBarTitle(R.string.routes)
                    navigateTo(RouteFragment.newInstance())
                    false
                }

            }
            sectionHeader("Storage")
            primaryItem("Persistent Volumes") {
                icon = R.drawable.pv
                onClick { _ ->
                    Log.d("DRAWER", "Persistent Volumes")
                    setActionBarTitle(R.string.persistent_volumes)
                    navigateTo(PVFragment.newInstance())
                    false
                }

            }
            primaryItem("Persistent Volume Claims") {
                icon = R.drawable.pvc
                onClick { _ ->
                    Log.d("DRAWER", "Persistent Volume Claims")
                    setActionBarTitle(R.string.persistent_volume_claims)
                    navigateTo(PVCFragment.newInstance())
                    false
                }
            }
            sectionHeader("Compute")
            primaryItem("Nodes") {
                icon = R.drawable.node
                onClick { _ ->
                    Log.d("DRAWER", "Nodes")
                    setActionBarTitle(R.string.nodes)
                    navigateTo(NodeFragment.newInstance())
                    false
                }
            }
        }
    }

    private fun navigateTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.homeFragment, fragment)
            .addToBackStack(null)
            .commit()
    }

    // Separate function to allow for any future action bar modifications
    private fun setActionBarTitle(title: Int) {
        supportActionBar?.setTitle(title)
    }
}