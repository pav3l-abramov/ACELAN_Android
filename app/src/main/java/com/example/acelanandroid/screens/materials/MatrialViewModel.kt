package com.example.acelanandroid.screens.materials

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.acelanandroid.retrofit.AppRetrofit
import com.example.acelanandroid.retrofit.GetDataApi
import com.example.acelanandroid.data.singleData.Material
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MatrialViewModel  @Inject constructor(
    private val appRetrofit: AppRetrofit
) : ViewModel() {
    val mainApi = appRetrofit.retrofit.create(GetDataApi::class.java)

    private val _dataListMaterial = MutableStateFlow<List<Material>>(emptyList())
    val dataListMaterial: StateFlow<List<Material>> = _dataListMaterial.asStateFlow()

    suspend fun getListMaterials(searchText:String, tokenUser:String) {
        Log.d("tasks", "start3")
        if (tokenUser != null) {
            val materials = mainApi.getMaterials("Bearer $tokenUser",searchText)
            _dataListMaterial.value = materials.materials
        }

    }
}