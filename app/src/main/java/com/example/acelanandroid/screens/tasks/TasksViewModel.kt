package com.example.acelanandroid.screens.tasks

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.acelanandroid.data.TaskMain
import com.example.acelanandroid.data.singleData.Login
import com.example.acelanandroid.retrofit.AppRetrofit
import com.example.acelanandroid.retrofit.GetDataApi
import com.example.acelanandroid.retrofit.GetStateTasks
import com.example.acelanandroid.retrofit.PostState
import com.example.acelanandroid.screens.StatusUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val appRetrofit: AppRetrofit
) : ViewModel() {
    val mainApi = appRetrofit.retrofit.create(GetDataApi::class.java)

    var uiCheckStatus = mutableStateOf(StatusUI())
    private val _tasksState = MutableLiveData<GetStateTasks>()
    val tasksState: LiveData<GetStateTasks> = _tasksState

    suspend fun getListTasksWithRetry(tokenUser: String) {
        try {
            getListTasks(tokenUser)
        } catch (e: Exception) {
            // Handle the error and retry the request if necessary
            getListTasks(tokenUser)
        }
    }

    private suspend fun getListTasks(tokenUser: String) {
            _tasksState.value = GetStateTasks.Loading
            uiCheckStatus.value = StatusUI("Loading","Loading")
            try {
                Log.d("getListTasks", "3")
                val response = mainApi.getTasks("Bearer $tokenUser")
                if (response.isSuccessful) {
                    Log.d("getListTasks", "4")
                    uiCheckStatus.value = StatusUI("Success","Success")
                    val tasks = response.body()
                    _tasksState.value = tasks?.let { GetStateTasks.Success(it) }
//                    job.cancel()
                } else {
                    _tasksState.value = GetStateTasks.Error("Login failed")
                    uiCheckStatus.value = StatusUI("Error","Login failed")
                }
                //TimeUnit.MILLISECONDS.sleep(200)
            } catch (e: Exception) {
                _tasksState.value = GetStateTasks.Error(e.message ?: "Error occurred")
            }

    }

    fun typeError(code: String) {
        uiCheckStatus.value = StatusUI("Error",code)
    }

    fun nullStatus() {
        uiCheckStatus.value = StatusUI()
    }
}