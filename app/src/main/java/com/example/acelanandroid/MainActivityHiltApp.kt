package com.example.acelanandroid

import android.app.Application
import android.util.Log
import com.example.acelanandroid.opengl.Model
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainActivityHiltApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        Log.d("ModelViewerApplication","1")
    }

    companion object {
        lateinit var instance: MainActivityHiltApp

        // Store the current model globally, so that we don't have to re-decode it upon
        // relaunching the main or VR activities.
        // TODO: handle this a bit better.
        var currentModel: Model? = null
    }
}