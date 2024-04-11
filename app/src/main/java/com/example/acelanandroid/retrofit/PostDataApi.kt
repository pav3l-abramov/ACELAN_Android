package com.example.acelanandroid.retrofit

import com.example.acelanandroid.data.singleData.Token
import com.example.acelanandroid.data.singleData.Login
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import javax.inject.Singleton


interface PostDataApi {
    @POST("/api/token")
    suspend fun auth(@Body login: Login): Response<Token>
}