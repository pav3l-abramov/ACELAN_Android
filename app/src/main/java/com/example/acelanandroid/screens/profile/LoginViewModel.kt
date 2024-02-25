package com.example.acelanandroid.screens.profile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.acelanandroid.data.singleData.Login
import com.example.acelanandroid.retrofit.PostDataApi
import com.example.acelanandroid.retrofit.AppRetrofit
import com.example.acelanandroid.retrofit.PostState
import com.example.acelanandroid.screens.StatusUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
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
    fun setEmailUI(emailDB:String){
        uiState.value = uiState.value.copy(email = emailDB)
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _loginState.value = PostState.Loading
            uiCheckStatus.value= StatusUI("Loading")
            try {
                val response = mainApi.auth(Login(email, password))
                if (response.isSuccessful) {
                    val token = response.body()
                    _loginState.value = token?.let { PostState.Success(it) }
                } else {
                    _loginState.value = PostState.Error("Login failed")
                    uiCheckStatus.value= StatusUI("Error")
                }
            } catch (e: Exception) {
                _loginState.value = PostState.Error(e.message ?: "Error occurred")
                uiCheckStatus.value= StatusUI("Error")
            }
        }
    }
    fun typeError(code:String){
        uiCheckStatus.value= StatusUI(code)
    }
    fun nullStatus(){uiCheckStatus.value= StatusUI(null)}
}
