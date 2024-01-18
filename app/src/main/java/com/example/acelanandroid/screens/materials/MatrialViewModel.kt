package com.example.acelanandroid.screens.materials

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.acelanandroid.dataStore.DataStoreManager
import com.example.acelanandroid.dataStore.UserData
import com.example.acelanandroid.retrofit.AppRetrofit
import com.example.acelanandroid.retrofit.PostDataApi
import com.example.acelanandroid.screens.profile.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MatrialViewModel  @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val appRetrofit: AppRetrofit
) : ViewModel() {


    val mainApi = appRetrofit.retrofit.create(PostDataApi::class.java)

    var uiState = mutableStateOf(LoginUiState())
        private set
    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

    val userData = dataStoreManager.getDataUser()


    var uiStateUser = mutableStateOf(UserData())
        private set

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }

    suspend fun checkUser() {
        userData.collect(){checkActiveUser->
            uiStateUser.value=uiStateUser.value.copy(isActive = checkActiveUser.isActive)
            uiStateUser.value = uiStateUser.value.copy(email = checkActiveUser.email)

        }
    }
    suspend fun onSignInClick() {
        val token = mainApi.auth(
            LoginUiState(email, password)
        )
        dataStoreManager.saveDataUser(UserData(email,password,token.token,true))
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
    suspend fun onLogOutClick() {
        dataStoreManager.deleteDataUser()

    }


}