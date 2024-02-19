package com.example.acelanandroid.retrofit


import com.example.acelanandroid.retrofit.data.Materials
import com.example.acelanandroid.retrofit.data.TaskDetails
import com.example.acelanandroid.retrofit.data.Tasks
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface GetDataApi {
    @GET("/api/tasks/")
    suspend fun getTasks(@Header("Authorization") token:String): Tasks

    @GET("/api/tasks/{id}")
    suspend fun getTaskDetails(@Header("Authorization") token:String, @Path("id") id:Int):TaskDetails

    @GET("/api/materials?search=")
    suspend fun getMaterials(@Header("Authorization") token:String): Materials
}

