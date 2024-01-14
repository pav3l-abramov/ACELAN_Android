package com.example.acelanandroid.connectToServer.repository

import com.example.acelanandroid.connectToServer.helpers.apiRequestFlow
import com.example.acelanandroid.connectToServer.retrofit.MainApiService
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val mainApiService: MainApiService,
) {
    fun getTasks() = apiRequestFlow {
        mainApiService.getTasks()
    }
}