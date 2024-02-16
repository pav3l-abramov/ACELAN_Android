package com.example.acelanandroid

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentResolverCompat
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
import java.io.IOException
import java.io.InputStream
import java.util.*

class OpenGLES20Activity : AppCompatActivity() {
    private lateinit var sampleModels: List<String>
    private var sampleModelIndex = 0
    private var modelView: ModelSurfaceView? = null
    private val disposables = CompositeDisposable()
    private val openDocumentLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (it.resultCode == Activity.RESULT_OK && it.data?.data != null) {
            val uri = it.data?.data
            grantUriPermission(packageName, uri, Intent.FLAG_GRANT_READ_URI_PERMISSION)
            beginLoadModel(uri!!)
        }
    }
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val modelUrl: String = intent.getStringExtra("url").toString()
        val modelType: String = intent.getStringExtra("type").toString()
        val model: Model? = null
        Log.d("modelUrl",modelUrl)
        setContentView(ModelSurfaceView(this, model))
        sampleModels = assets.list("")!!.filter { it.endsWith(".stl") }

        if (intent.data != null && savedInstanceState == null) {
            beginLoadModel(intent.data!!)
        }
    }
    override fun onStart() {
        super.onStart()
        Log.d("onStart","5")
           createNewModelView(MainActivityHiltApp.currentModel)
        if (MainActivityHiltApp.currentModel != null) {
            Log.d("onStart","4")
            title = MainActivityHiltApp.currentModel!!.title
            Log.d("onStart","3")
        }
        Log.d("onStart","1")
        loadSampleModel()
        Log.d("onStart","2")
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
    private fun beginLoadModel(uri: Uri) {
        Log.d("beginLoadModel","1")
        disposables.add(
            Single.fromCallable {
            var model: Model? = null
            var stream: InputStream? = null
            try {
                val cr = applicationContext.contentResolver
                val fileName = getFileName(cr, uri)
                stream = if ("http" == uri.scheme || "https" == uri.scheme) {
                    val client = OkHttpClient()
                    val request: Request = Request.Builder().url(uri.toString()).build()
                    val response = client.newCall(request).execute()

                    // TODO: figure out how to NOT need to read the whole file at once.
                    ByteArrayInputStream(response.body!!.bytes())
                } else {
                    cr.openInputStream(uri)
                }
                if (stream != null) {
                    if (!fileName.isNullOrEmpty()) {
                        model = when {
                            fileName.lowercase(Locale.ROOT).endsWith(".stl") -> {
                                StlModel(stream)
                            }
                            fileName.lowercase(Locale.ROOT).endsWith(".obj") -> {
                                ObjModel(stream)
                            }
                            fileName.lowercase(Locale.ROOT).endsWith(".ply") -> {
                                PlyModel(stream)
                            }
                            else -> {
                                Log.d("beginLoadModel","2")
                                // assume it's STL.
                                StlModel(stream)
                            }
                        }
                        model.title = fileName
                    } else {
                        // assume it's STL.
                        // TODO: autodetect file type by reading contents?
                        model = StlModel(stream)
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
                //binding.progressBar.visibility = View.GONE
            }
            .subscribe({
                setCurrentModel(it)
            }, {
                it.printStackTrace()
                Toast.makeText(applicationContext, "Some problems", Toast.LENGTH_SHORT).show()
            }))
    }
    private fun getFileName(cr: ContentResolver, uri: Uri): String? {
        Log.d("getFileName","1")
        if ("content" == uri.scheme) {
            val projection = arrayOf(MediaStore.MediaColumns.DISPLAY_NAME)
            ContentResolverCompat.query(cr, uri, projection, null, null, null, null)?.use { metaCursor ->
                if (metaCursor.moveToFirst()) {
                    return metaCursor.getString(0)
                }
            }
        }
        return uri.lastPathSegment
    }
    private fun setCurrentModel(model: Model) {
        createNewModelView(model)
        Toast.makeText(applicationContext, "Success open model", Toast.LENGTH_SHORT).show()
        title = model.title
    }
    private fun loadSampleModel() {
        Log.d("loadSampleModel","1")
        try {
            val stream = assets.open(sampleModels[sampleModelIndex++ % sampleModels.size])
            setCurrentModel(StlModel(stream))
            stream.close()
            Log.d("loadSampleModel","2")
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d("loadSampleModel","3")
        }
    }
}