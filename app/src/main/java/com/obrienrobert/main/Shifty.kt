package com.obrienrobert.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class Shifty : Application(), AnkoLogger {
    lateinit var app: Shifty

    override fun onCreate() {
        super.onCreate()
        info("Shifty app started")
    }
}