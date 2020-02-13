package com.obrienrobert.kubely

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import co.zsmb.materialdrawerkt.builders.drawer
import co.zsmb.materialdrawerkt.draweritems.badgeable.primaryItem
import co.zsmb.materialdrawerkt.draweritems.sectionHeader
import com.obrienrobert.client.Client
import com.obrienrobert.fragments.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class Main : AppCompatActivity(), AnkoLogger {

    private val fragmentManager = supportFragmentManager
    private val homeFragment = HomeFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // Display First Fragment initially
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.homeFragment, homeFragment)
        fragmentTransaction.commit()

        // Test client
        val client = Client("<MASTER_URL>", "<TOKEN>")

        // Cannot execute network calls on the main thread
        GlobalScope.async {
            info("${client.getPods()}")
        }

        // Nav draw setup
        drawer {
            primaryItem("Home") {
                onClick { _ ->
                    Log.d("DRAWER", "Home")
                    setActionBarTitle(R.string.app_name)
                    navigateTo(HomeFragment.newInstance())
                    false
                }
            }

            sectionHeader("Workloads")
            primaryItem("Deployments") {
                icon = R.drawable.deployment
                onClick { _ ->
                    Log.d("DRAWER", "Deployments")
                    setActionBarTitle(R.string.deployments)
                    navigateTo(DeploymentFragment.newInstance())
                    false
                }

            }
            primaryItem("Pods") {
                icon = R.drawable.pod
                onClick { _ ->
                    Log.d("DRAWER", "Pods")
                    setActionBarTitle(R.string.pods)
                    navigateTo(PodFragment.newInstance())
                    false
                }

            }

            sectionHeader("Networking")
            primaryItem("Ingresses") {
                icon = R.drawable.ingress
                onClick { _ ->
                    Log.d("DRAWER", "Ingresses")
                    setActionBarTitle(R.string.ingresses)
                    navigateTo(IngressFragment.newInstance())
                    false
                }

            }
            primaryItem("Services") {
                icon = R.drawable.service
                onClick { _ ->
                    Log.d("DRAWER", "Services")
                    setActionBarTitle(R.string.services)
                    navigateTo(ServiceFragment.newInstance())
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