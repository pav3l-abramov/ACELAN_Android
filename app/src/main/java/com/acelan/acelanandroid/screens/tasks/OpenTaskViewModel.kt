package com.acelan.acelanandroid.screens.tasks

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acelan.acelanandroid.retrofit.AppRetrofit
import com.acelan.acelanandroid.retrofit.GetDataApi
import com.acelan.acelanandroid.retrofit.GetStateTaskDetail
import com.acelan.acelanandroid.screens.StatusUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OpenTaskViewModel @Inject constructor(
    private val appRetrofit: AppRetrofit
) : ViewModel() {

    private val mainApi: GetDataApi = appRetrofit.retrofit.create(GetDataApi::class.java)

var uiCheckStatus = mutableStateOf(StatusUI())
    private val _taskDetailState = MutableLiveData<GetStateTaskDetail>()
    val taskDetailState: LiveData<GetStateTaskDetail> = _taskDetailState

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun getListTaskDetailWithRetry(tokenUser: String,idTask:Int,context: Context) {
        try {
            getListTaskDetail(tokenUser,idTask,context)
        } catch (e: Exception) {
            // Handle the error and retry the request if necessary
            //getListTaskDetail(tokenUser,idTask)
        }
    }
    private fun getListTaskDetail(tokenUser: String,idTask:Int,context: Context) {
        viewModelScope.launch {
            _isLoading.value = true
            _taskDetailState.value = GetStateTaskDetail.Loading
            uiCheckStatus.value = StatusUI("Loading", "Loading")
            try {
                Log.d("getListTasks", "3")
                val response = mainApi.getTaskDetails("Bearer $tokenUser", idTask)
                if (response.isSuccessful) {
                    Toast.makeText(
                        context,
                        "Success",
                        Toast.LENGTH_SHORT
                    ).show()
                    _isLoading.value = false
                    uiCheckStatus.value = StatusUI("Success", "Success")
                    Log.d("getListTasks", "4")
                    val tasks = response.body()
                    _taskDetailState.value = tasks?.let { GetStateTaskDetail.Success(it) }
//                    job.cancel()
                } else {
                    _isLoading.value = false
                    _taskDetailState.value = GetStateTaskDetail.Error("Login failed")
                    Toast.makeText(
                        context,
                        "Login failed",
                        Toast.LENGTH_SHORT
                    ).show()
                    uiCheckStatus.value = StatusUI("Error", "Login failed")
                }
                //TimeUnit.MILLISECONDS.sleep(200)
            } catch (e: Exception) {
                _taskDetailState.value = GetStateTaskDetail.Error(e.message ?: "Error occurred")
                Toast.makeText(
                    context,
                    e.message ?: "Error occurred",
                    Toast.LENGTH_SHORT
                ).show()
                _isLoading.value = false
            }
        }

    }
    fun typeError(code: String) {
        uiCheckStatus.value = StatusUI("Error",code)
    }

    fun nullStatus() {
        uiCheckStatus.value = StatusUI()
    }
}