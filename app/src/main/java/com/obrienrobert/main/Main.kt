package com.obrienrobert.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import co.zsmb.materialdrawerkt.builders.accountHeader
import co.zsmb.materialdrawerkt.builders.drawer
import co.zsmb.materialdrawerkt.draweritems.badgeable.primaryItem
import co.zsmb.materialdrawerkt.draweritems.profile.profile
import co.zsmb.materialdrawerkt.draweritems.sectionHeader
import co.zsmb.materialdrawerkt.imageloader.drawerImageLoader
import com.google.firebase.auth.FirebaseAuth
import com.mikepenz.materialdrawer.util.DrawerUIUtils
import com.obrienrobert.fragments.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.startActivity


class Main : AppCompatActivity(), AnkoLogger {

    lateinit var app: Shifty

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        app = application as Shifty
        app.auth = FirebaseAuth.getInstance()

        drawerImageLoader {
            placeholder { ctx, _ ->
                DrawerUIUtils.getPlaceHolder(ctx)
            }
            set { imageView, uri, placeholder, _ ->
                Picasso.get()
                    .load(uri)
                    .placeholder(placeholder)
                    .into(imageView)
            }
            cancel { imageView ->
                Picasso.get()
                    .cancelRequest(imageView)
            }
        }

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
        setActionBarTitle(R.string.clusters)

        // If the user is not a Google user, no username will exist. Otherwise, load the Google image.
        var userName = ""
        if (!app.auth.currentUser?.displayName.isNullOrEmpty()) {
            userName = app.auth.currentUser?.displayName!!
        }

        // Nav draw setup
        drawer {
            accountHeader {
                profile(userName, app.auth.currentUser?.email) {
                    if (app.auth.currentUser?.photoUrl.toString().isNotEmpty()) {
                        iconUrl =
                            app.auth.currentUser!!.photoUrl.toString().replace("s96-c", "s400-c")
                    }
                }
            }
            sectionHeader("Home").divider = false
            primaryItem("Clusters") {
                icon = R.drawable.openshift
                onClick { _ ->
                    setActionBarTitle(R.string.clusters)
                    navigateTo(ClusterFragment.newInstance())
                    false
                }
            }
            primaryItem("Projects") {
                icon = R.drawable.project
                onClick { _ ->
                    setActionBarTitle(R.string.projects)
                    navigateTo(ProjectFragment.newInstance())
                    false
                }
            }
            primaryItem("Events") {
                icon = R.drawable.events
                onClick { _ ->
                    setActionBarTitle(R.string.namespace_events)
                    navigateTo(EventFragment.newInstance())
                    false
                }
            }
            sectionHeader("Workloads")
            primaryItem("Pods") {
                icon = R.drawable.pod
                onClick { _ ->
                    setActionBarTitle(R.string.pods)
                    navigateTo(PodFragment.newInstance())
                    false
                }

            }
            primaryItem("Deployments") {
                icon = R.drawable.deployment
                onClick { _ ->
                    setActionBarTitle(R.string.deployments)
                    navigateTo(DeploymentFragment.newInstance())
                    false
                }

            }
            primaryItem("Secrets") {
                icon = R.drawable.secret
                onClick { _ ->
                    setActionBarTitle(R.string.secrets)
                    navigateTo(SecretFragment.newInstance())
                    false
                }

            }
            primaryItem("Cron Jobs") {
                icon = R.drawable.cron_job
                onClick { _ ->
                    setActionBarTitle(R.string.cron_jobs)
                    navigateTo(CronJobFragment.newInstance())
                    false
                }

            }
            sectionHeader("Networking")
            primaryItem("Services") {
                icon = R.drawable.service
                onClick { _ ->
                    setActionBarTitle(R.string.services)
                    navigateTo(ServiceFragment.newInstance())
                    false
                }

            }
            primaryItem("Routes") {
                icon = R.drawable.route
                onClick { _ ->
                    setActionBarTitle(R.string.routes)
                    navigateTo(RouteFragment.newInstance())
                    false
                }

            }
            sectionHeader("Storage")
            primaryItem("Persistent Volumes") {
                icon = R.drawable.pv
                onClick { _ ->
                    setActionBarTitle(R.string.persistent_volumes)
                    navigateTo(PVFragment.newInstance())
                    false
                }

            }
            primaryItem("Persistent Volume Claims") {
                icon = R.drawable.pvc
                onClick { _ ->
                    setActionBarTitle(R.string.persistent_volume_claims)
                    navigateTo(PVCFragment.newInstance())
                    false
                }
            }
            sectionHeader("Compute")
            primaryItem("Nodes") {
                icon = R.drawable.node
                onClick { _ ->
                    setActionBarTitle(R.string.nodes)
                    navigateTo(NodeFragment.newInstance())
                    false
                }
            }
            sectionHeader("Access Control")
            primaryItem("Sign Out") {
                icon = R.drawable.signout
                onClick { _ ->
                    app.auth.signOut()
                    app.googleSignInClient.signOut()
                    startActivity<Login>()
                    finish()
                    false
                }
            }
        }
    }

    private fun navigateTo(fragment: Fragment) {

        supportFragmentManager.apply {
            beginTransaction()
                .replace(R.id.homeFragment, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun setActionBarTitle(title: Int) {
        supportActionBar?.setTitle(title)
    }
}