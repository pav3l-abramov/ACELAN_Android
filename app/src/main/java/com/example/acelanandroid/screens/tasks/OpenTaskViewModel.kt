package com.example.acelanandroid.screens.tasks

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.acelanandroid.data.TaskMain
import com.example.acelanandroid.retrofit.AppRetrofit
import com.example.acelanandroid.retrofit.GetDataApi
import com.example.acelanandroid.data.singleData.Artifact
import com.example.acelanandroid.data.singleData.TaskDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class OpenTaskViewModel @Inject constructor(
    private val appRetrofit: AppRetrofit
) : ViewModel() {

    val mainApi = appRetrofit.retrofit.create(GetDataApi::class.java)
//    var uiState = mutableStateOf(Artifact())
//        private set
//
//    var uiStateMain = mutableStateOf(TaskDetails())
//        private set

    private val _dataList = MutableStateFlow(TaskMain())
    val dataList: StateFlow<TaskMain> = _dataList.asStateFlow()

    suspend fun getTaskById(tokenUser:String,idTask:Int) {
        Log.d("tasks", "start4")
        Log.d("task detail", "start5")
        val task =  mainApi.getTaskDetails("Bearer $tokenUser", idTask)
        val taskMainList =
            TaskMain(
                id = task.id,
                name = task.name,
                status = task.status,
                started_at = task.started_at ?: "",
                finished_at = task.finished_at?: "",
                url=task.artifacts?.get(0)?.url.toString()?: "",
                file_type = task.artifacts?.get(0)?.file_type.toString()?: "",
                x= task.figures?.get(0)?.data?.x ?: emptyList(),
                y= task.figures?.get(0)?.data?.y ?: emptyList(),
            )
        _dataList.value=taskMainList
    }
}