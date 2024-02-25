package com.example.acelanandroid.screens.materials

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.acelanandroid.retrofit.AppRetrofit
import com.example.acelanandroid.retrofit.GetDataApi
import com.example.acelanandroid.data.MaterialMain
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OpenMaterialViewModel @Inject constructor(
    private val appRetrofit: AppRetrofit
) : ViewModel() {
    val mainApi = appRetrofit.retrofit.create(GetDataApi::class.java)
    var idMaterial: Int? = null


//    var uiState = mutableStateOf(Properties())
//        private set

    var uiStateMain = mutableStateOf(MaterialMain())
        private set


    fun getIdMaterial(newValue: Int) {
        idMaterial = newValue
    }


    suspend fun getMaterialById(token:String, idMaterial:Int) {
        val material = idMaterial.let { mainApi.getMaterialDetails("Bearer $token", it) }
        uiStateMain.value = uiStateMain.value.copy(name = material.name)
        uiStateMain.value = uiStateMain.value.copy(type = material.type)
        uiStateMain.value = uiStateMain.value.copy(source = material.source)
        uiStateMain.value = uiStateMain.value.copy(created_at = material.created_at)
        uiStateMain.value = uiStateMain.value.copy(updated_at = material.updated_at)
        uiStateMain.value = uiStateMain.value.copy(core = material.core)
        uiStateMain.value = uiStateMain.value.copy(young = material.properties?.young)
        uiStateMain.value = uiStateMain.value.copy(poison = material.properties?.poison)

//                if (!material.properties.isNullOrEmpty()) {
//                    uiState.value = uiState.value.copy(stiffness = material.properties[0].stiffness)
//                }

    }

}