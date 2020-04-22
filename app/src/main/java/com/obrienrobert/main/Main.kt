package com.obrienrobert.main

import android.content.Intent
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
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.mikepenz.materialdrawer.Drawer
import com.obrienrobert.fragments.*
import com.obrienrobert.util.readImageUri
import com.obrienrobert.util.uploadImageView
import com.obrienrobert.util.writeImageRef
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.profile.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.startActivity


class Main : AppCompatActivity(), AnkoLogger {

    lateinit var app: Shifty
    lateinit var result: Drawer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        app = application as Shifty
        app.auth = FirebaseAuth.getInstance()

        drawerImageLoader {
            set { imageView, uri, _, _ ->
                Glide.with(applicationContext).load(uri).into(imageView)
            }
            cancel { imageView ->
                Glide.with(applicationContext).clear(imageView)
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
        supportActionBar?.title = R.string.clusters.toString()

        // If the user is not a Google user, no username will exist. Otherwise, load the Google image.
        var userName = ""
        if (!app.auth.currentUser?.displayName.isNullOrEmpty()) {
            userName = app.auth.currentUser?.displayName!!
        }

        // Nav draw setup
        result = drawer {
            accountHeader {
                profile(userName, app.auth.currentUser?.email) {
                    if (!app.auth.currentUser?.displayName.isNullOrEmpty()) {
                        iconUrl =
                            app.auth.currentUser!!.photoUrl.toString().replace("s96-c", "s400-c")
                    }else{
                        icon = R.drawable.profile
                    }
                }
                background = R.color.md_black_1000
                textColorRes = R.color.md_white_1000
            }
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
            sectionHeader("Access Control")
            primaryItem("Profile") {
                icon = R.drawable.profile
                onClick { _ ->
                    navigateTo(ProfileFragment.newInstance())
                    false
                }
            }
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> {
                if (data != null) {
                    writeImageRef(app, readImageUri(resultCode, data).toString())
                    Picasso.get().load(readImageUri(resultCode, data).toString())
                        .resize(180, 180)
                        .transform(CropCircleTransformation())
                        .into(profile_image, object : Callback {
                            override fun onSuccess() {
                                uploadImageView(app, profile_image)
                            }

                            override fun onError(e: Exception) {}
                        })
                }
            }
        }
    }

    companion object {
        fun newInstance(): Main {
            return Main()
        }
    }
}