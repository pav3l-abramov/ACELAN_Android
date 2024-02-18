package com.example.acelanandroid


import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.example.acelanandroid.opengl.Model
import com.example.acelanandroid.opengl.ModelSurfaceView
import com.example.acelanandroid.opengl.model.ObjModel
import com.example.acelanandroid.opengl.model.PlyModel
import com.example.acelanandroid.opengl.model.StlModel
import com.example.acelanandroid.opengl.model.Util.closeSilently
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.ByteArrayInputStream
import java.io.InputStream

class OpenGLES20Activity : AppCompatActivity() {
    private var modelView: ModelSurfaceView? = null
    private val disposables = CompositeDisposable()
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val modelUrl: Uri? = intent.getStringExtra("url")?.toUri()
        val modelType: String = intent.getStringExtra("type").toString()
        val model: Model? = null
        setContentView(ModelSurfaceView(this, model))
            if (modelUrl != null && savedInstanceState == null) {
                beginLoadModel(modelUrl,modelType)
            }
    }
    override fun onStart() {
        super.onStart()
           createNewModelView(MainActivityHiltApp.currentModel)
        if (MainActivityHiltApp.currentModel != null) {
            title = MainActivityHiltApp.currentModel!!.title
        }
    }
    override fun onPause() {
        super.onPause()
        modelView?.onPause()
    }
    override fun onResume() {
        super.onResume()
        modelView?.onResume()
    }
    override fun onDestroy() {
        disposables.clear()
        super.onDestroy()
    }
    private fun createNewModelView(model: Model?) {
        setContentView(ModelSurfaceView(this, model))
    }
    private fun beginLoadModel(uri: Uri,ModelType:String) {
        disposables.add(
            Single.fromCallable {
            var model: Model? = null
            var stream: InputStream? = null
            try {
                val cr = applicationContext.contentResolver
                stream = if ("http" == uri.scheme || "https" == uri.scheme) {
                    val client = OkHttpClient()
                    val request: Request = Request.Builder().url(uri.toString()).build()
                    val response = client.newCall(request).execute()
                    ByteArrayInputStream(response.body!!.bytes())
                } else {
                    cr.openInputStream(uri)
                }
                if (stream != null) {
                    if (ModelType.isNotEmpty()) {
                        model = when {
                            ModelType=="stl" -> {
                                StlModel(stream)
                            }
                            ModelType=="obj" -> {
                                ObjModel(stream)
                            }
                            ModelType=="ply" -> {
                                PlyModel(stream)
                            }
                            else -> {
                                Toast.makeText(applicationContext, "i don't know this format", Toast.LENGTH_SHORT).show()
                                StlModel(stream)
                            }
                        }
                    } else {
                        Toast.makeText(applicationContext, "type format is empty", Toast.LENGTH_SHORT).show()
                    }
                }
                MainActivityHiltApp.currentModel = model
                model!!
            } finally {
                closeSilently(stream)
            }
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doAfterTerminate {
            }
            .subscribe({
                setCurrentModel(it)
            }, {
                it.printStackTrace()
                Toast.makeText(applicationContext, "Some problems", Toast.LENGTH_SHORT).show()
            }))
    }
    private fun setCurrentModel(model: Model) {
        createNewModelView(model)
        Toast.makeText(applicationContext, "Success open model", Toast.LENGTH_SHORT).show()
        title = model.title
    }
}