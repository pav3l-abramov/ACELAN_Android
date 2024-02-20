package com.example.acelanandroid.screens.tasks

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.acelanandroid.dataStore.DataStoreManager
import com.example.acelanandroid.retrofit.AppRetrofit
import com.example.acelanandroid.retrofit.GetDataApi
import com.example.acelanandroid.data.singleData.Artifact
import com.example.acelanandroid.data.singleData.TaskDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OpenTaskViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val appRetrofit: AppRetrofit
) : ViewModel() {

    val mainApi = appRetrofit.retrofit.create(GetDataApi::class.java)

    val userData = dataStoreManager.getDataUser()
    var tokenUser: String? = null
    var idTask: Int? = null


    var uiState = mutableStateOf(Artifact())
        private set

    var uiStateMain = mutableStateOf(TaskDetails())
        private set


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
            Log.d("task detail", "start5")
            val task = idTask?.let { mainApi.getTaskDetails("Bearer $tokenUser", it) }
            if (task != null) {
                uiStateMain.value = uiStateMain.value.copy(name = task.name)
                uiStateMain.value = uiStateMain.value.copy(status = task.status)
                uiStateMain.value = uiStateMain.value.copy(started_at = task.started_at)
                uiStateMain.value = uiStateMain.value.copy(finished_at = task.finished_at)
                if (!task.artifacts.isNullOrEmpty()) {
                    uiState.value = uiState.value.copy(url = task.artifacts?.get(0)?.url.toString())
                    uiState.value = uiState.value.copy(file_type = task.artifacts?.get(0)?.file_type.toString())
                }

            }
        }
    }
}