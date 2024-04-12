package com.acelan.acelanandroid.retrofit

import com.acelan.acelanandroid.data.singleData.Token
import com.acelan.acelanandroid.data.singleData.Login
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST


interface PostDataApi {
    @POST("/api/token")
    suspend fun auth(@Body login: Login): Response<Token>
}