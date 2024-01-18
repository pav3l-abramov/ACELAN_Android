package com.example.acelanandroid.screens.tasks

import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.example.acelanandroid.dataStore.DataStoreManager
import com.example.acelanandroid.retrofit.AppRetrofit
import com.example.acelanandroid.retrofit.GetDataApi
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.File
import java.io.FileWriter
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class OpenTaskViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val appRetrofit: AppRetrofit
) : ViewModel() {

    val mainApi = appRetrofit.retrofit.create(GetDataApi::class.java)

    val userData = dataStoreManager.getDataUser()
    var tokenUser: String? = null
    var idTask:Int? = null

    fun getIdTask(newValue: Int) {
        idTask = newValue
    }

    suspend fun getToken() {
        Log.d("tasks", "start1")

        userData.collect() { data ->
            tokenUser = data.token
        }
        Log.d("tasks", "start2")
    }
    suspend fun getTaskById() {
        Log.d("tasks", "start4")
        if (tokenUser != null) {
            Log.d("task detail","start5")
            val task = idTask?.let { mainApi.getTaskDetails("Bearer $tokenUser", it) }
            //_dataList.value = tasks.tasks
            Log.d("task detail","start6")
            Log.d("task detail", task.toString())
            //  _dataDetail.value = task
        }

    }

    suspend fun downloadFile(url: String, file: File) {
        val request = Request.Builder()
            .url(url)
            .build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object : Callback {
            override fun onResponse(call: Call, response: Response) {
                val writer = FileWriter(file)
                writer.write(response.body!!.string())
                writer.close()
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.e("TAG", "Error downloading file: $e")
            }
        })
    }
}