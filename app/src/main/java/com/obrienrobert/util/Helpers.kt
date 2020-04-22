package com.obrienrobert.util

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.AdaptiveIconDrawable
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.graphics.drawable.toBitmap
import androidx.core.net.toUri
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.tasks.OnFailureListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.obrienrobert.fragments.ProfileFragment
import com.obrienrobert.main.R
import com.obrienrobert.main.Shifty
import com.obrienrobert.models.UserPhotoModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.profile.*
import java.io.ByteArrayOutputStream
import java.io.IOException


fun createLoader(activity: FragmentActivity): AlertDialog {
    val loaderBuilder = AlertDialog.Builder(activity)
        .setCancelable(true)
        .setView(R.layout.loading)
    val loader = loaderBuilder.create()
    loader.setTitle(R.string.app_name)
    loader.setIcon(R.drawable.openshift)

    return loader
}

fun showLoader(loader: AlertDialog, message: String) {
    if (!loader.isShowing) {
        loader.setTitle(message)
        loader.show()
    }
}

fun hideLoader(loader: AlertDialog) {
    if (loader.isShowing)
        loader.dismiss()
}

fun writeImageRef(app: Shifty, imageRef: String) {
    val userId = app.auth.currentUser!!.uid
    val values = UserPhotoModel(userId, imageRef).toMap()
}


fun convertImageToBytes(imageView: ImageView): ByteArray {

    val bitmap: Bitmap = if (imageView is AdaptiveIconDrawable || imageView is AppCompatImageView)
        imageView.drawable.toBitmap()
    else
        (imageView.drawable as BitmapDrawable).toBitmap()

    val byteOutputStream = ByteArrayOutputStream()
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteOutputStream)
    return byteOutputStream.toByteArray()
}

fun uploadImageView(app: Shifty, imageView: ImageView) {
    val uid = app.auth.currentUser!!.uid
    val imageRef = app.storage.child("photos").child("${uid}.jpg")
    val uploadTask = imageRef.putBytes(convertImageToBytes(imageView))

    uploadTask.addOnFailureListener {
        OnFailureListener { error -> Log.d("Test", "uploadTask.exception$error") }
    }.addOnSuccessListener {
        uploadTask.continueWithTask { task ->
            imageRef.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                app.userImage = task.result!!.toString().toUri()
                writeImageRef(app, app.userImage.toString())
                Picasso.get().load(app.userImage)
                    .resize(200, 200)
                    .transform(CropCircleTransformation())
                    .into(imageView)
            }
        }
    }
}


fun showImagePicker(parent: Activity, id: Int) {
    val intent = Intent()
    intent.type = "image/*"
    intent.action = Intent.ACTION_OPEN_DOCUMENT
    intent.addCategory(Intent.CATEGORY_OPENABLE)
    val chooser = Intent.createChooser(intent, R.string.select_profile_image.toString())
    parent.startActivityForResult(chooser, id)
}

fun readImageUri(resultCode: Int, data: Intent?): Uri? {
    var uri: Uri? = null
    if (resultCode == Activity.RESULT_OK && data != null && data.data != null) {
        try {
            uri = data.data
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
    return uri
}


fun validatePhoto(app: Shifty, activity: Activity) {
    var imageUri: Uri? = null
    val imageExists = app.userImage.toString().isNotEmpty()
    val googlePhotoExists = app.auth.currentUser?.photoUrl != null

    if (imageExists) imageUri = app.userImage
    else if (googlePhotoExists) imageUri = app.auth.currentUser?.photoUrl!!

    if (googlePhotoExists || imageExists) {
        Picasso.get().load(imageUri)
            .resize(180, 180)
            .transform(CropCircleTransformation())
            .into(ProfileFragment.newInstance().profile_image, object : Callback {
                override fun onSuccess() {
                    uploadImageView(
                        app,
                        ProfileFragment.newInstance().profile_image
                    )
                }

                override fun onError(e: Exception) {}
            })
    } else {   // New Regular User, upload default pic of homer
        ProfileFragment.newInstance().profile_image
            .setImageResource(R.drawable.profile)
        uploadImageView(app, ProfileFragment.newInstance().profile_image)
    }
}

fun checkExistingPhoto(app: Shifty, activity: Activity) {

    app.userImage = "".toUri()
    app.database.child("user-photos").orderByChild("uid")
        .equalTo(app.auth.currentUser!!.uid)
        .addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    val usermodel = it.getValue(UserPhotoModel::class.java)
                    app.userImage = usermodel!!.profilePicture.toUri()
                }
                validatePhoto(app, activity)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
}

