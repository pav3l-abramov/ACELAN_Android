package com.example.acelanandroid.screens.tasks

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.room.TypeConverters
import com.example.acelanandroid.data.FloatListConverter
import com.example.acelanandroid.data.TaskMain
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

    private val _dataList = MutableStateFlow<List<TaskMain>>(emptyList())
    val dataList: StateFlow<List<TaskMain>> = _dataList.asStateFlow()

    suspend fun getListTasks(tokenUser:String) {
        Log.d("tasks", "start3")
        if (tokenUser != null) {
            val tasks = mainApi.getTasks("Bearer $tokenUser")
            val taskMainList = tasks.tasks.map {
                TaskMain(
                    id = it.id,
                    name = it.name,
                    status = it.status,
                    started_at = it.started_at,
                    finished_at = it.finished_at,
                )
            }
            _dataList.value = taskMainList
        }
        Log.d("getListTasks", "getListTasksgetListTasksgetListTasksgetListTasks")
    }
}