package com.example.acelanandroid.screens.profile

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.acelanandroid.data.singleData.Login
import com.example.acelanandroid.data.UserData
import com.example.acelanandroid.retrofit.PostDataApi
import com.example.acelanandroid.retrofit.AppRetrofit
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val appRetrofit: AppRetrofit
) : ViewModel() {


    val mainApi = appRetrofit.retrofit.create(PostDataApi::class.java)

    var uiState = mutableStateOf(Login())
        private set
    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

    var uiStateUser = mutableStateOf(UserData())
        private set

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }


    suspend fun onSignInClick() {
        val token = mainApi.auth(
            Login(email, password)
        )
        uiStateUser.value= uiStateUser.value.copy(email=uiState.value.email)
        uiStateUser.value= uiStateUser.value.copy(token=token.token)
        //uiStateUser.value=uiStateUser.value.copy(isActive = true)
//        uiStateToken.value= uiStateToken.value.copy(token = user.token)
//        if (!email.isValidEmail()) {
//            SnackbarManager.showMessage(AppText.email_error)
//            return
//        }
//
//        if (password.isBlank()) {
//            SnackbarManager.showMessage(AppText.empty_password_error)
//            return
//        }

    }
}