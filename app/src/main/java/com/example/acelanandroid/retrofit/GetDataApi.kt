package com.example.acelanandroid.retrofit


import com.example.acelanandroid.data.singleData.MaterialDetails
import com.example.acelanandroid.data.singleData.Materials
import com.example.acelanandroid.data.singleData.TaskDetails
import com.example.acelanandroid.data.singleData.Tasks
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface GetDataApi {
    @GET("/api/tasks/")
    suspend fun getTasks(@Header("Authorization") token:String): Response<Tasks>

    @GET("/api/tasks/{id}")
    suspend fun getTaskDetails(@Header("Authorization") token:String, @Path("id") id:Int): TaskDetails

    @GET("/api/materials")
    suspend fun getMaterials(@Header("Authorization") token:String,@Query("search") search:String): Materials

    @GET("/api/materials/{id}")
    suspend fun getMaterialDetails(@Header("Authorization") token:String, @Path("id") id:Int): MaterialDetails
}

