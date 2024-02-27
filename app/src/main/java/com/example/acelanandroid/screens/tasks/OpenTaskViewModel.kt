package com.example.acelanandroid.screens.tasks

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.acelanandroid.data.TaskMain
import com.example.acelanandroid.retrofit.AppRetrofit
import com.example.acelanandroid.retrofit.GetDataApi
import com.example.acelanandroid.retrofit.GetStateTaskDetail
import com.example.acelanandroid.retrofit.GetStateTasks
import com.example.acelanandroid.screens.StatusUI
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OpenTaskViewModel @Inject constructor(
    private val appRetrofit: AppRetrofit
) : ViewModel() {

    private val mainApi: GetDataApi = appRetrofit.retrofit.create(GetDataApi::class.java)

var uiCheckStatus = mutableStateOf(StatusUI())
    private val _taskDetailState = MutableLiveData<GetStateTaskDetail>()
    val taskDetailState: LiveData<GetStateTaskDetail> = _taskDetailState

    suspend fun getListTaskDetailWithRetry(tokenUser: String,idTask:Int) {
        try {
            getListTaskDetail(tokenUser,idTask)
        } catch (e: Exception) {
            // Handle the error and retry the request if necessary
            getListTaskDetail(tokenUser,idTask)
        }
    }

    private suspend fun getListTaskDetail(tokenUser: String,idTask:Int) {
        _taskDetailState.value = GetStateTaskDetail.Loading
        uiCheckStatus.value = StatusUI("Loading","Loading")
        try {
            Log.d("getListTasks", "3")
            val response = mainApi.getTaskDetails("Bearer $tokenUser",idTask)
            if (response.isSuccessful) {
                uiCheckStatus.value = StatusUI("Success","Success")
                Log.d("getListTasks", "4")
                val tasks = response.body()
                _taskDetailState.value = tasks?.let { GetStateTaskDetail.Success(it) }
//                    job.cancel()
            } else {
                _taskDetailState.value = GetStateTaskDetail.Error("Login failed")
                uiCheckStatus.value = StatusUI("Error","Login failed")
            }
            //TimeUnit.MILLISECONDS.sleep(200)
        } catch (e: Exception) {
            _taskDetailState.value = GetStateTaskDetail.Error(e.message ?: "Error occurred")
        }

    }
    fun typeError(code: String) {
        uiCheckStatus.value = StatusUI("Error",code)
    }

    fun nullStatus() {
        uiCheckStatus.value = StatusUI()
    }
}