package com.example.acelanandroid.connectToServer.viewModel

import androidx.lifecycle.MutableLiveData
import com.example.acelanandroid.connectToServer.helpers.ApiResponse
import com.example.acelanandroid.connectToServer.viewModel.BaseViewModel
import com.example.acelanandroid.connectToServer.viewModel.CoroutinesErrorHandler
import com.example.acelanandroid.connectToServer.repository.AuthRepository
import com.example.acelanandroid.connectToServer.retrofit.response.Auth
import com.example.acelanandroid.connectToServer.retrofit.response.LoginResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
): BaseViewModel() {

    private val _loginResponse = MutableLiveData<ApiResponse<LoginResponse>>()
    val loginResponse = _loginResponse

    fun login(auth: Auth, coroutinesErrorHandler: CoroutinesErrorHandler) = baseRequest(
        _loginResponse,
        coroutinesErrorHandler
    ) {
        authRepository.login(auth)
    }
}