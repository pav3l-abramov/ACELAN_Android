package com.acelan.acelanandroid.screens.materials

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
import com.acelan.acelanandroid.retrofit.GetStateMaterial
import com.acelan.acelanandroid.screens.StatusUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatrialViewModel  @Inject constructor(
    private val appRetrofit: AppRetrofit
) : ViewModel() {



    val mainApi = appRetrofit.retrofit.create(GetDataApi::class.java)
    var uiCheckStatus = mutableStateOf(StatusUI())
    private val _materialsState = MutableLiveData<GetStateMaterial>()
    val materialsState: LiveData<GetStateMaterial> = _materialsState

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    fun getListMaterialsWithRetry(searchText:String,tokenUser: String,context: Context, onSearch:Boolean) {
        try {
            getListMaterials(searchText,tokenUser,context,onSearch)
        } catch (e: Exception) {
            // Handle the error and retry the request if necessary
            //getListTasks(tokenUser,context)
        }
    }


    private fun getListMaterials(searchText:String,tokenUser: String, context: Context,onSearch:Boolean) {
        viewModelScope.launch {
            _isLoading.value = !onSearch
            _materialsState.value = GetStateMaterial.Loading
            uiCheckStatus.value = StatusUI("Loading", "Loading")
            try {
                Log.d("getListTasks", "3")
                val response = mainApi.getMaterials("Bearer $tokenUser",searchText)
                if (response.isSuccessful) {
                    if(!onSearch) {
                        Toast.makeText(
                            context,
                            "Success",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    _isLoading.value = false
                    Log.d("getListTasks", "4")
                    uiCheckStatus.value = StatusUI("Success", "Success")
                    val materials = response.body()
                    _materialsState.value = materials?.let { GetStateMaterial.Success(it,onSearch) }
                } else {
                    _isLoading.value = false
                    Toast.makeText(
                        context,
                        "Login failed",
                        Toast.LENGTH_SHORT
                    ).show()
                    _materialsState.value = GetStateMaterial.Error("Login failed")
                    uiCheckStatus.value = StatusUI("Error", "Login failed")

                }
            } catch (e: Exception) {
                _materialsState.value = GetStateMaterial.Error(e.message ?: "Error occurred")
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

}