package com.obrienrobert.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.google.firebase.auth.FirebaseAuth
import com.obrienrobert.main.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.profile.*
import org.jetbrains.anko.AnkoLogger


class ProfileFragment : BaseFragment(), AnkoLogger {

    override fun layoutId() = R.layout.profile

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        app.auth = FirebaseAuth.getInstance()

        if (app.auth.currentUser?.photoUrl.toString().isNotEmpty()) {
            Picasso.get()
                .load(app.auth.currentUser!!.photoUrl.toString().replace("s96-c", "s400-c").toUri())
                .placeholder(R.drawable.profile)
                .into(profile_image)
        }

        profile_user_name.text = app.auth.currentUser?.displayName

        profile_user_info.text = app.auth.currentUser?.email
    }

    override fun onResume() {
        super.onResume()
        (this.activity as AppCompatActivity?)!!.supportActionBar!!.setTitle(R.string.profile)
    }

    companion object {
        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }
}