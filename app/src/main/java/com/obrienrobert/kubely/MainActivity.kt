package com.obrienrobert.kubely

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import co.zsmb.materialdrawerkt.builders.drawer
import co.zsmb.materialdrawerkt.draweritems.badgeable.primaryItem
import co.zsmb.materialdrawerkt.draweritems.divider
import com.obrienrobert.fragments.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.AnkoLogger

class MainActivity : AppCompatActivity(), AnkoLogger{

    private val fragmentManager = supportFragmentManager
    private val homeFragment = HomeFragment()
    private val podFragment = PodFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        /* Display First Fragment initially */
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.homeFragment, homeFragment)
        fragmentTransaction.commit()

        drawer {
            primaryItem("Home") {
                onClick { _ ->
                    Log.d("DRAWER", "Home")
                    navigateTo(HomeFragment.newInstance())
                    false
                }
            }
            divider {}
            primaryItem("Pods") {
                onClick { _ ->
                    Log.d("DRAWER", "Pods")
                    navigateTo(PodFragment.newInstance())
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
}