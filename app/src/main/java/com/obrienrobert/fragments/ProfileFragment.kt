package com.obrienrobert.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.firebase.auth.FirebaseAuth
import com.obrienrobert.main.Main
import com.obrienrobert.main.R
import com.obrienrobert.util.checkExistingPhoto
import com.obrienrobert.util.showImagePicker
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.profile.*
import org.jetbrains.anko.AnkoLogger


class ProfileFragment : BaseFragment(), AnkoLogger {

    override fun layoutId() = R.layout.profile

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        app.auth = FirebaseAuth.getInstance()

        val uid = app.auth.currentUser!!.uid
        val imageRef = app.storage.child("photos").child("${uid}.jpg")

        if (!app.auth.currentUser?.displayName.isNullOrEmpty()) {
            Picasso.get()
                .load(app.auth.currentUser!!.photoUrl.toString().replace("s96-c", "s400-c").toUri())
                .placeholder(R.drawable.profile)
                .into(profile_image)
        } else {
            // Update image in profile
            Glide.with(this)
                .load(imageRef)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .placeholder(R.drawable.profile)
                .into(profile_image)
        }

        profile_user_name.text = app.auth.currentUser?.displayName

        profile_user_info.text = app.auth.currentUser?.email

        checkExistingPhoto(app, activity as Main)

        profile_image.setOnClickListener {
            showImagePicker(activity as Main, 1)
        }
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