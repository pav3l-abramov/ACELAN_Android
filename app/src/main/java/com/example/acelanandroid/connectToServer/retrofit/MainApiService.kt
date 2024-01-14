package com.example.acelanandroid.connectToServer.retrofit

import com.example.acelanandroid.connectToServer.retrofit.response.TasksResponse
import retrofit2.Response
import retrofit2.http.GET

interface MainApiService {
    @GET("/api/tasks/")
    suspend fun getTasks(): Response<TasksResponse>
}