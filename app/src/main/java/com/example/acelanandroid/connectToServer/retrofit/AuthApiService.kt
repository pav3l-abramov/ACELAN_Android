package com.example.acelanandroid.connectToServer.retrofit

import com.example.acelanandroid.connectToServer.retrofit.response.Auth
import com.example.acelanandroid.connectToServer.retrofit.response.LoginResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApiService {
    @POST("/api/token")
    suspend fun login(
        @Body auth: Auth,
    ): Response<LoginResponse>

    //если на сервере это будет реализовано, это уже будет готово
    @GET("url")
    suspend fun refreshToken(
        @Header("Authorization") token: String,
    ): Response<LoginResponse>
}