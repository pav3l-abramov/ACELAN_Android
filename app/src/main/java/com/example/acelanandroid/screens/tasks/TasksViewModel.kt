package com.example.acelanandroid.screens.tasks

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.acelanandroid.dataStore.DataStoreManager
import com.example.acelanandroid.retrofit.AppRetrofit
import com.example.acelanandroid.retrofit.GetDataApi
import com.example.acelanandroid.data.singleData.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val appRetrofit: AppRetrofit
) : ViewModel() {
    val mainApi = appRetrofit.retrofit.create(GetDataApi::class.java)

    private val _dataList = MutableStateFlow<List<Task>>(emptyList())
    val dataList: StateFlow<List<Task>> = _dataList.asStateFlow()

    suspend fun getListTasks(tokenUser:String) {
        Log.d("tasks", "start3")
        if (tokenUser != null) {
            val tasks = mainApi.getTasks("Bearer $tokenUser")
            _dataList.value = tasks.tasks
        }

    }
}