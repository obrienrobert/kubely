package com.obrienrobert.main

import android.app.Application
import android.net.Uri
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class Shifty: Application(), AnkoLogger {

    lateinit var auth: FirebaseAuth
    lateinit var database: DatabaseReference
    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var storage: StorageReference

    override fun onCreate() {
        super.onCreate()
        info("Shifty app started")
    }
}