package com.example.acelanandroid.retrofit

import com.example.acelanandroid.retrofit.data.User
import com.example.acelanandroid.screens.profile.LoginUiState
import retrofit2.http.Body
import retrofit2.http.POST

interface PostDataApi {
    @POST("/api/token")
    suspend fun auth(@Body loginUiState: LoginUiState): User

}