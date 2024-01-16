package com.example.acelanandroid.screens.profile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.acelanandroid.retrofit.PostDataApi
import com.example.acelanandroid.screens.AppRetrofit
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val appViewModel: AppRetrofit
) : ViewModel() {

    //    val retrofit = Retrofit.Builder()
//        .baseUrl(HOST_NAME)
//        .client(buildOkHttpClient())
//        .addConverterFactory(GsonConverterFactory.create()).build()
//
//    private fun buildOkHttpClient(): OkHttpClient {
//        val interceptor = HttpLoggingInterceptor().apply {
//            level = HttpLoggingInterceptor.Level.BODY
//        }
//        return OkHttpClient.Builder()
//            .addInterceptor(interceptor)
//            .build()
//    }
    val mainApi = appViewModel.retrofit.create(PostDataApi::class.java)

    var uiState = mutableStateOf(LoginUiState())
        private set
//// не забыть удалить
//    var uiStateToken = mutableStateOf(User(""))
//        private set
//    private val token
//        get() = uiStateToken.value.token

    private val email
        get() = uiState.value.email
    private val password
        get() = uiState.value.password

    fun onEmailChange(newValue: String) {
        uiState.value = uiState.value.copy(email = newValue)
    }

    fun onPasswordChange(newValue: String) {
        uiState.value = uiState.value.copy(password = newValue)
    }


    suspend fun onSignInClick() {
        val token = mainApi.auth(
            LoginUiState(email, password)
        )
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