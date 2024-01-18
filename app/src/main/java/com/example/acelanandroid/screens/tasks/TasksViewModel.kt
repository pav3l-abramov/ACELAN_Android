package com.example.acelanandroid.screens.tasks

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.acelanandroid.dataStore.DataStoreManager
import com.example.acelanandroid.dataStore.UserData
import com.example.acelanandroid.retrofit.AppRetrofit
import com.example.acelanandroid.retrofit.GetDataApi
import com.example.acelanandroid.retrofit.PostDataApi
import com.example.acelanandroid.retrofit.data.Task
import com.example.acelanandroid.retrofit.data.Tasks
import com.example.acelanandroid.screens.profile.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TasksViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val appRetrofit: AppRetrofit
) : ViewModel() {
    val mainApi = appRetrofit.retrofit.create(GetDataApi::class.java)
    val userData = dataStoreManager.getDataUser()


    var tokenUser: String? = null
    private val _dataList = MutableStateFlow<List<Task>>(emptyList())   // Инициализируем пустым списком
    val dataList: StateFlow<List<Task>> = _dataList.asStateFlow()
    var uiStateCheck= mutableStateOf(false)
    private set
    suspend fun getToken() {
        Log.d("tasks", "start1")

        userData.collect() { data ->
            tokenUser = data.token
        }
        Log.d("tasks", "start2")
    }

    suspend fun getListTasks() {
        Log.d("tasks", "start3")
        if (tokenUser != null) {

            val tasks = mainApi.getTasks("Bearer $tokenUser")
            _dataList.value = tasks.tasks
            uiStateCheck.value=true
            Log.d("tasks", dataList.value.toString())
        }

    }

}