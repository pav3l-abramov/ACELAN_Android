package com.example.acelanandroid.screens.materials

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.acelanandroid.retrofit.AppRetrofit
import com.example.acelanandroid.retrofit.GetDataApi
import com.example.acelanandroid.data.MaterialMain
import com.example.acelanandroid.retrofit.GetStateMaterialDetail
import com.example.acelanandroid.retrofit.GetStateTaskDetail
import com.example.acelanandroid.screens.StatusUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OpenMaterialViewModel @Inject constructor(
    private val appRetrofit: AppRetrofit
) : ViewModel() {
    val mainApi = appRetrofit.retrofit.create(GetDataApi::class.java)

    var uiCheckStatus = mutableStateOf(StatusUI())
    private val _materialDetailState = MutableLiveData<GetStateMaterialDetail>()
    val materialDetailState: LiveData<GetStateMaterialDetail> = _materialDetailState

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun getListMaterialDetailWithRetry(tokenUser: String, idTask: Int, context: Context) {
        try {
            getListTaskDetail(tokenUser, idTask, context)
        } catch (e: Exception) {
            // Handle the error and retry the request if necessary
            //getListTaskDetail(tokenUser,idTask)
        }
    }

    private fun getListTaskDetail(tokenUser: String, idMaterial: Int, context: Context) {
        viewModelScope.launch {
            _isLoading.value = true
            _materialDetailState.value = GetStateMaterialDetail.Loading
            uiCheckStatus.value = StatusUI("Loading", "Loading")
            try {
                Log.d("getListTasks", "3")
                val response = mainApi.getMaterialDetails("Bearer $tokenUser", idMaterial)
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
                    _materialDetailState.value = tasks?.let { GetStateMaterialDetail.Success(it) }
//                    job.cancel()
                } else {
                    _isLoading.value = false
                    _materialDetailState.value = GetStateMaterialDetail.Error("Login failed")
                    Toast.makeText(
                        context,
                        "Login failed",
                        Toast.LENGTH_SHORT
                    ).show()
                    uiCheckStatus.value = StatusUI("Error", "Login failed")
                }
                //TimeUnit.MILLISECONDS.sleep(200)
            } catch (e: Exception) {
                _materialDetailState.value = GetStateMaterialDetail.Error(e.message ?: "Error occurred")
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
        uiCheckStatus.value = StatusUI("Error", code)
    }

    fun nullStatus() {
        uiCheckStatus.value = StatusUI()
    }

//    suspend fun getMaterialById(token:String, idMaterial:Int) {
//        val material = idMaterial.let { mainApi.getMaterialDetails("Bearer $token", it) }
//        uiStateMain.value = uiStateMain.value.copy(name = material.name)
//        uiStateMain.value = uiStateMain.value.copy(type = material.type)
//        uiStateMain.value = uiStateMain.value.copy(source = material.source)
//        uiStateMain.value = uiStateMain.value.copy(created_at = material.created_at)
//        uiStateMain.value = uiStateMain.value.copy(updated_at = material.updated_at)
//        uiStateMain.value = uiStateMain.value.copy(core = material.core)
//        uiStateMain.value = uiStateMain.value.copy(young = material.properties?.young)
//        uiStateMain.value = uiStateMain.value.copy(poison = material.properties?.poison)
//
////                if (!material.properties.isNullOrEmpty()) {
////                    uiState.value = uiState.value.copy(stiffness = material.properties[0].stiffness)
////                }
//
//    }

}