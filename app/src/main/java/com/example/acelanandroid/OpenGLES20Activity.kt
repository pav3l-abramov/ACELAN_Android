package com.example.acelanandroid

import android.app.Activity
import android.opengl.GLSurfaceView
import android.os.Bundle
import android.util.Log
import com.example.acelanandroid.opengl.MyGLSurfaceView
import com.example.acelanandroid.screens.tasks.ModelData

class OpenGLES20Activity : Activity() {

    private lateinit var gLView: GLSurfaceView

    public override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        val modelUrl:String = intent.getStringExtra("url").toString()
        val modelType:String = intent.getStringExtra("type").toString()
        // Create a GLSurfaceView instance and set it
        // as the ContentView for this Activity.
        Log.d("modelUri", modelUrl)
        Log.d("modelType", modelType)
        gLView = MyGLSurfaceView(this)
        setContentView(gLView)
    }

}