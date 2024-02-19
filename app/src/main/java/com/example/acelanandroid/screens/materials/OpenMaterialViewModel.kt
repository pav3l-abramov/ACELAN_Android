package com.example.acelanandroid.screens.materials

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.acelanandroid.dataStore.DataStoreManager
import com.example.acelanandroid.retrofit.AppRetrofit
import com.example.acelanandroid.retrofit.GetDataApi
import com.example.acelanandroid.retrofit.data.Artifact
import com.example.acelanandroid.retrofit.data.MaterialDetails
import com.example.acelanandroid.retrofit.data.Properties
import com.example.acelanandroid.retrofit.data.TaskDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OpenMaterialViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val appRetrofit: AppRetrofit
) : ViewModel() {
    val mainApi = appRetrofit.retrofit.create(GetDataApi::class.java)

    val userData = dataStoreManager.getDataUser()
    var tokenUser: String? = null
    var idMaterial: Int? = null


    var uiState = mutableStateOf(Properties())
        private set

    var uiStateMain = mutableStateOf(MaterialDetails())
        private set


    fun getIdMaterial(newValue: Int) {
        idMaterial = newValue
    }

    suspend fun getToken() {
        userData.collect() { data ->
            tokenUser = data.token
        }
    }

    suspend fun getMaterialById() {
        if (tokenUser != null) {
            val material = idMaterial?.let { mainApi.getMaterialDetails("Bearer $tokenUser", it) }
            if (material != null) {
                uiStateMain.value = uiStateMain.value.copy(name = material.name)
                uiStateMain.value = uiStateMain.value.copy(type = material.type)
                uiStateMain.value = uiStateMain.value.copy(source = material.source)
                uiStateMain.value = uiStateMain.value.copy(created_at = material.created_at)
                uiStateMain.value = uiStateMain.value.copy(updated_at = material.updated_at)
                uiStateMain.value = uiStateMain.value.copy(core = material.core)

//                if (!material.properties.isNullOrEmpty()) {
//                    uiState.value = uiState.value.copy(stiffness = material.properties[0].stiffness)
//                }

            }
        }
    }

}