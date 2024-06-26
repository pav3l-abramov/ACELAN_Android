package com.acelan.acelanandroid.screens.profile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.acelan.acelanandroid.data.singleData.Login
import com.acelan.acelanandroid.retrofit.PostDataApi
import com.acelan.acelanandroid.retrofit.AppRetrofit
import com.acelan.acelanandroid.retrofit.PostState
import com.acelan.acelanandroid.screens.StatusUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val appRetrofit: AppRetrofit
) : ViewModel() {

    private val _loginState = MutableLiveData<PostState>()
    val loginState: LiveData<PostState> = _loginState
    val mainApi = appRetrofit.retrofit.create(PostDataApi::class.java)

    var uiState = mutableStateOf(Login())
        private set
    var uiCheckStatus = mutableStateOf(StatusUI())

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    fun loginWithRetry(email: String, password: String) {
        try {
            login(email,password)
        } catch (e: Exception) {
            // Handle the error and retry the request if necessary

        }
    }

    private fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = PostState.Loading
            uiCheckStatus.value = StatusUI("Loading","Loading")
            try {
                val response = mainApi.auth(Login(email, password))
                if (response.isSuccessful) {
                    val token = response.body()
                    uiCheckStatus.value = StatusUI("Success","Success")
                    _loginState.value = token?.let { PostState.Success(it) }
                } else {
                    _loginState.value = PostState.Error("Login failed")
                    uiCheckStatus.value = StatusUI("Error","Login failed")
                }
            } catch (e: Exception) {
                _loginState.value = PostState.Error(e.message ?: "Error occurred")
            }
        }
    }
    fun typeError(code:String){
        uiCheckStatus.value= StatusUI("Error",code)
    }
    fun nullStatus(){uiCheckStatus.value= StatusUI(null)}
}
