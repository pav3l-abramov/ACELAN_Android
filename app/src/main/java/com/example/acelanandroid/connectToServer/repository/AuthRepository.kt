package com.example.acelanandroid.connectToServer.repository

import com.example.acelanandroid.connectToServer.helpers.apiRequestFlow
import com.example.acelanandroid.connectToServer.retrofit.AuthApiService
import com.example.acelanandroid.connectToServer.retrofit.response.Auth
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val authApiService: AuthApiService,
) {
    fun login(auth: Auth) = apiRequestFlow {
        authApiService.login(auth)
    }
}