package com.obrienrobert.main

import android.app.Application
import android.net.Uri
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import com.mikepenz.materialdrawer.AccountHeader
import com.mikepenz.materialdrawer.Drawer
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class Shifty : Application(), AnkoLogger {

    lateinit var auth: FirebaseAuth
    lateinit var database: DatabaseReference
    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var userImage: Uri
    lateinit var storage: StorageReference
    lateinit var result: Drawer
    lateinit var headerResult: AccountHeader
    lateinit var app: Shifty

    override fun onCreate() {
        super.onCreate()
        info("Shifty app started")
    }
}