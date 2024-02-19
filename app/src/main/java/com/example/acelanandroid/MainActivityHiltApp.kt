package com.example.acelanandroid

import android.app.Application
import com.example.acelanandroid.opengl.Model
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainActivityHiltApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        lateinit var instance: MainActivityHiltApp
        var currentModel: Model? = null
    }
}