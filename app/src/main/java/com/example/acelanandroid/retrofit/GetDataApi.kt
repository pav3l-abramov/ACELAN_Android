package com.example.acelanandroid.retrofit

import com.example.acelanandroid.retrofit.data.TaskDetails
import com.example.acelanandroid.retrofit.data.Tasks
import retrofit2.http.GET
import retrofit2.http.Path

interface GetDataApi {
    @GET("/api/tasks/")
    suspend fun getTasks():Tasks

    @GET("/api/tasks/{id}")
    fun getTaskDetails(@Path("id") id:Int):TaskDetails
}

