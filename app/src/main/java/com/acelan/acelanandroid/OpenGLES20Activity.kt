package com.acelan.acelanandroid


import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.acelan.acelanandroid.opengl.Model
import com.acelan.acelanandroid.opengl.OpenGLSurfaceView
import com.acelan.acelanandroid.opengl.model.ObjModel
import com.acelan.acelanandroid.opengl.model.PlyModel
import com.acelan.acelanandroid.opengl.model.StlModel
import com.acelan.acelanandroid.opengl.model.Util.closeSilently
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.ByteArrayInputStream
import java.io.InputStream

class OpenGLES20Activity : AppCompatActivity() {
    private val disposables = CompositeDisposable()

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val modelUrl: Uri? = intent.getStringExtra("url")?.toUri()
        val modelType: String = intent.getStringExtra("type").toString()
        setContentView(OpenGLSurfaceView(this, MainActivityHiltApp.currentModel))
        if (modelUrl != null && savedInstanceState == null) {
            loadModel(modelUrl, modelType)
        }
    }

    private fun loadModel(uri: Uri, modelType: String) {
        disposables.add(
            Single.fromCallable {
                val model: Model?
                var stream: InputStream? = null
                try {
                    val client = OkHttpClient()
                    val request: Request = Request.Builder().url(uri.toString()).build()
                    val response = client.newCall(request).execute()
                    stream = ByteArrayInputStream(response.body!!.bytes())
                    model = when (modelType) {
                        "stl" -> {
                            StlModel(stream)
                        }

                        "obj" -> {
                            ObjModel(stream)
                        }

                        "ply" -> {
                            PlyModel(stream)
                        }

                        else -> {
                            Toast.makeText(
                                applicationContext,
                                "i don't know this format",
                                Toast.LENGTH_SHORT
                            ).show()
                            StlModel(stream)
                        }
                    }
                    MainActivityHiltApp.currentModel = model
                    model
                } finally {
                    closeSilently(stream)
                }
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    setContentView(OpenGLSurfaceView(this, it))
                }, {
                    it.printStackTrace()
                    Toast.makeText(applicationContext, "Some problems", Toast.LENGTH_SHORT).show()
                })
        )
    }
}